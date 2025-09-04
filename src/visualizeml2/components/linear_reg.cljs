(ns visualizeml2.components.linear-reg
  (:require
   [re-frame.core :as rf]
   [visualizeml2.subs :as subs]
   [visualizeml2.components.katex :as katex]
   ))

(defn b0b1-display []
  (when @(rf/subscribe [::subs/set-optimized-loss])
    [:div
     [:h1.mt-4.is-size-4.has-text-white.is-family-monospace "\nCalculating b1 & b0...\n"]
     [:h1.mt-2.is-size-4.has-text-white.is-family-monospace "b1 = "
      [:span (.toFixed (js/Number @(rf/subscribe [::subs/linear-b1])) 2)]]
     [:h1.is-size-4.has-text-white.is-family-monospace "b0 = "
      [:span (.toFixed (js/Number @(rf/subscribe [::subs/linear-b0])) 2)]]]))

(defn explanation-text []
  (when @(rf/subscribe [::subs/set-optimized-loss])
    [:div.columns
     [:div.column.is-5
      [:p.has-text-white.is-family-monospace "To optimize loss is to find the params such that loss is minimized. This is also known as the line of 'best fit'. In order to do that, we need to find dL/db0 and dL/db1 and set these derivatives to 0."]]]))

;; (defn math-html [equation]
;;   (katex/renderToString equation #js {:throwOnError false}))



(defn workings []
  (when @(rf/subscribe [::subs/set-optimized-loss])
    [:div.is-size-4.has-text-white
     [:h1.is-family-monospace.pt-4.mb-2.is-size-3.is-italic "📝 Workings:"]
     [:h1.pt-4 [katex/katex "L = \\sum_{i=1}^{n} (y_i - \\hat{y}_i)^2"]]
     [:h1.pt-4 [katex/katex "L = \\sum_{i=1}^{n} (y_i - b_0-b_1x_i)^2"]]
     [:h1.pt-4 [katex/katex "\\frac{dL}{db_0} = 2\\sum(y_i - b_0-b_1x_i)(-1)"]]
     [:h1.pt-4.has-text-warning [katex/katex "\\frac{dL}{db_0} = -2\\sum(y_i - b_0-b_1x_i)"]]

     [:h1.pt-4.is-family-monospace.is-italic.is-size-5 "Equating " [katex/katex "\\frac{dL}{db_0}"] " to 0 and solving for " [katex/katex "b_0:"]]
     [:h1.pt-4 [katex/katex "-2\\sum(y_i - b_0-b_1x_i) = 0"]]
     [:h1.pt-4 [katex/katex "\\sum{y_i} - nb_0-b_1\\sum{x_i} = 0"]]
     [:h1.pt-4 [katex/katex "nb_0 = \\sum{y_i} -b_1\\sum{x_i}"]]
     [:h1.pt-4 [katex/katex "b_0 = \\frac{\\sum{y_i} -b_1\\sum{x_i}}{n}"]]
     [:h1.pt-4 [katex/katex "b_0 = \\frac{\\sum{y_i}}{n} - b_1\\frac{\\sum{x_i}}{n}"]]
     [:h1.pt-4.has-text-warning [katex/katex "b_0 = \\bar{y}-b_1\\bar{x}\n"]]

     [:h1.pt-4.is-family-monospace.is-italic.is-size-5 "Getting " [katex/katex "\\frac{dL}{db_1}:"]]
     [:h1.pt-4 [katex/katex "\\frac{dL}{db_1} = 2\\sum(y_i - b_0-b_1x_i)(-x_i)"]]
     [:h1.pt-4.has-text-warning [katex/katex "\\frac{dL}{db_1} = -2\\sum(y_i - b_0-b_1x_i)(x_i)"]]

     [:h1.pt-4.is-family-monospace.is-italic.is-size-5 "Equating " [katex/katex "\\frac{dL}{db_1}"] " to 0 and solving for " [katex/katex "b_1:"]]
     [:h1.pt-4 [katex/katex "-2\\sum(y_i - b_0-b_1x_i)(x_i)=0"]]
     [:h1.pt-4 [katex/katex "\\sum(y_i - (\\bar{y}-b_1\\bar{x})-b_1x_i)(x_i)=0"]]
     [:h1.pt-4 [katex/katex "\\sum(y_i - \\bar{y}+b_1\\bar{x}-b_1x_i)(x_i)=0"]]
     [:h1.pt-4 [katex/katex "\\sum(y_i - \\bar{y}-b_1x_i+b_1\\bar{x})(x_i)=0"]]
     [:h1.pt-4 [katex/katex "\\sum((y_i - \\bar{y})-b_1(x_i-\\bar{x}))(x_i)=0"]]
     [:h1.pt-4 [katex/katex "\\sum(y_i - \\bar{y})-b_1\\sum(x_i-\\bar{x})(x_i)=0"]]
     [:h1.pt-4.has-text-warning [katex/katex "b_1 = \\frac{\\sum(y_i - \\bar{y})}{\\sum(x_i-\\bar{x})(x_i)}"] " or " [katex/katex "\\frac{\\sum{(y_i - \\bar{y})(x_i-\\bar{x})}}{\\sum{(x_i-\\bar{x})(x_i-\\bar{x})}}"]]
     ]))