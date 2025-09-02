(ns visualizeml2.math.equations
  (:require
   [re-frame.core :as rf]
   [visualizeml2.subs :as subs]
   [visualizeml2.math.utils :as utils]))

(defn linear-loss-eqn-css [eqn]
  (let [show? @(rf/subscribe [::subs/show-linear-loss-eqn])]
    [utils/math-formula
     eqn
     {:display? false
      :style {:visibility (if show? "visible" "hidden")
              :margin ""
              :color "white"
              :font-size "2.5em"}}]))

(defn optimize-loss-workings []
  (let [b0 (:b0 (:optimal-params @(rf/subscribe [::subs/linear-deps])))
        b1 (:b1 (:optimal-params @(rf/subscribe [::subs/linear-deps])))]
    (when @(rf/subscribe [::subs/show-workings])
      [:div
       [:p "To optimize loss is to find the params such that loss is minimized. This is also known as the line of 'best fit'. In order to do that, we need to find dL/db0 and dL/db1 and set these derivatives to 0."]
       [:p "Insert workings"]
       [:p "Optimal b0 found:" b0]
       [:p "Optimal b1 found:" b1]
       [:button {:on-click #(rf/dispatch [:optimize-params b0 b1])}
        "Set params!"]])))

(defn loss-equation []
  [:div.column.is-4.debug.pt-5
   [linear-loss-eqn-css "L = \\sum_{i=1}^{n} (y_i - \\hat{y}_i)^2"]
   (when @(rf/subscribe [::subs/show-linear-loss-eqn])
     [:div.is-size-2.has-text-white.is-family-monospace.pl-6.pt-2 (str "= " (.toFixed (:loss @(rf/subscribe [::subs/linear-deps])) 2))])])