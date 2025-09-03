(ns visualizeml2.components.buttons
  (:require
   [re-frame.core :as rf]
   [visualizeml2.math.generate :as generate]
   [visualizeml2.subs :as subs]))

(defn generate-data-button []
  [:button.button.is-outlined.is-info {:on-click #(rf/dispatch [:update-linear-data-and-params! (generate/generate-data 10)])} "Generate-data"])

(defn estimate-best-fit-button []
  [:button.button.is-outlined.is-info {:on-click #(rf/dispatch [:show-estimate-line])} "Draw rough line of best fit"])

(defn calculate-loss-button []
  (when @(rf/subscribe [::subs/show-estimate-line])
    [:button.button.is-outlined.is-info {:on-click #(rf/dispatch [:toggle-linear-eqn])} "Calculate Loss"]))

(defn optimize-loss-button []
  (when @(rf/subscribe [::subs/show-linear-loss-eqn])
    (let [params (:optimal-params @(rf/subscribe [::subs/linear-deps]))
          b0 (:b0 params)
          b1 (:b1 params)]
      [:div [:button.button.is-outlined.is-info.is-medium.is-fullwidth {:on-click #(rf/dispatch [:optimize-loss b0 b1])}
             "Optimize Loss!"]]
      )))
