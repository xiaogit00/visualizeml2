(ns visualizeml2.views
  (:require
   [re-frame.core :as rf]
   [reagent.core :as r]
   [visualizeml2.subs :as subs]
   [visualizeml2.math.generate :as generate]
   [visualizeml2.charts :as charts]
   [visualizeml2.math.utils :as utils]
   ))



(defn linear-loss-eqn-css [eqn]
  (let [show? @(rf/subscribe [::subs/show-linear-loss-eqn])]
    [utils/math-formula
     eqn
     {:display? false
      :style {:visibility (if show? "visible" "hidden")
              :margin "1rem 0"}}]))

(defn linear-regression []
  [:div
   [:h1 "Linear Regression"]
   [:button {:on-click 
             #(rf/dispatch [:generate-data-and-params (generate/generate-data 10)])
             }"Generate-data"]
   [charts/scatter-plot 
    @(rf/subscribe [::subs/linear-data])
    @(rf/subscribe [::subs/show-estimate-line])]
   [:button {:on-click 
             #(rf/dispatch [:toggle-estimate-line])}
    "Estimate Line of Best Fit"]
   [:p @(rf/subscribe [::subs/linear-fn-text])]
   [:div 
    [:label {:for "b1"} "b1: "]
    [:input#b1 {:type "text"
                :value @(rf/subscribe [::subs/linear-b1])
                :on-change (fn [e] (rf/dispatch [:update-linear-b1 (-> e .-target .-value)]))}]
    [:label {:for "b0"} "b0: "]
    [:input#b0 {:type "text"
                :value @(rf/subscribe [::subs/linear-b0])
                :on-change (fn [e] (rf/dispatch [:update-linear-b0 (-> e .-target .-value)]))}]]
   [:button {:on-click 
             #(rf/dispatch [:render-loss])}
    "Calculate Loss"]
   [linear-loss-eqn-css "L = \\sum_{i=1}^{n} (y_i - \\hat{y}_i)^2 ="]
   (when @(rf/subscribe [::subs/show-linear-loss-eqn]) [:span @(rf/subscribe [::subs/linear-loss])])
   
   ])


(defn main-panel [] 
  [:div
   [:div
    [linear-regression]
    ]])
