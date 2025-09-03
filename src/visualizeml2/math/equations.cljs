(ns visualizeml2.math.equations
  (:require
   [re-frame.core :as rf]
   [visualizeml2.subs :as subs]
   [visualizeml2.math.utils :as utils]
   [visualizeml2.components.katex :as katex]))


(defn loss-equation []
  [:div.column.is-4.debug.pt-4
   
   (when @(rf/subscribe [::subs/show-linear-loss-eqn])
     [:<>
      [:h1.is-size-3.has-text-white [katex/katex "L = \\sum_{i=1}^{n} (y_i - \\hat{y}_i)^2"]]
      [:div.is-size-2.has-text-white.is-family-monospace.pl-6.pt-2 (str "= " (.toFixed (:loss @(rf/subscribe [::subs/linear-deps])) 2))]])])