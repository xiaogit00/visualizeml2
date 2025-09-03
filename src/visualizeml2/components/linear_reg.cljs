(ns visualizeml2.components.linear-reg
  (:require
   [re-frame.core :as rf]
   [visualizeml2.subs :as subs]
   ["katex" :as katex]
   [visualizeml2.components.katex :as katex-component]))

(defn b0b1-display []
  (when @(rf/subscribe [::subs/set-optimized-loss])
    [:div
     [:h1.mt-4.is-size-4.has-text-white.is-family-monospace "\nCalculating b1 & b0...\n"]
     [:h1.mt-2.is-size-4.has-text-white.is-family-monospace "b1 = "
      [:span (.toFixed @(rf/subscribe [::subs/linear-b1]) 2)]]
     [:h1.is-size-4.has-text-white.is-family-monospace "b0 = "
      [:span (.toFixed @(rf/subscribe [::subs/linear-b0]) 2)]]]))

(defn explanation-text []
  (when @(rf/subscribe [::subs/set-optimized-loss])
    [:div.columns
     [:div.column.is-5
      [:p.has-text-white.is-family-monospace "To optimize loss is to find the params such that loss is minimized. This is also known as the line of 'best fit'. In order to do that, we need to find dL/db0 and dL/db1 and set these derivatives to 0."]]]))

;; (defn math-html [equation]
;;   (katex/renderToString equation #js {:throwOnError false}))

(defn math-component [equation]
  [:div 
   [:span.has-text-white
    {:dangerouslySetInnerHTML
     {:__html (katex/renderToString equation #js {:throwOnError false})}}]])

(defn workings []
  (when @(rf/subscribe [::subs/set-optimized-loss])
    [:<>
     [:h1.is-family-monospace.pt-4.mb-2.is-size-3.has-text-white.is-italic "📝 Workings:"]
     [katex-component/katex-component {:equation "E = mc^2" :class "tag is-info"}]
     ]))