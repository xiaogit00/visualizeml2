(ns visualizeml2.views
  (:require
   [re-frame.core :as rf]
   [visualizeml2.subs :as subs]
   [visualizeml2.math.generate :as generate]
   [visualizeml2.charts :as charts]
   ))

(defn math-formula
  [latex & [{:keys [display?]}]]
  (let [wrapped (if display?
                  (str "\\[" latex "\\]")   ;; block math
                  (str "\\(" latex "\\)"))] ;; inline math
    [:span {:dangerouslySetInnerHTML {:__html wrapped}}]))

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
   [:label {:for "b1"} "b1: "]
   [:input#b1 {:type "text"
               :value @(rf/subscribe [::subs/linear-b1])
               :on-change (fn [e] (rf/dispatch [:update-linear-b1 (-> e .-target .-value)]))}]
   [:label {:for "b0"} "b0: "]
   [:input#b0 {:type "text"
               :value @(rf/subscribe [::subs/linear-b0])
               :on-change (fn [e] (rf/dispatch [:update-linear-b0 (-> e .-target .-value)]))}]
   [math-formula "L = \\sum_{i=1}^{n} (y_i - \\hat{y}_i)^2"]
   
   
   ])


(defn main-panel [] 
  [:div
   [:div
    [linear-regression]
    ]])
