(ns visualizeml2.components.inputs
  (:require [re-frame.core :as rf]
            [visualizeml2.subs :as subs]))

(defn b1-input []
  [:div.column.is-half
   [:label.label.has-text-danger {:for "b1"} "b1: "]
   [:input#b1.input.is-transparent.has-text-warning {:type "text"
                                                     :value @(rf/subscribe [::subs/linear-b1])
                                                     :placeholder "b0"
                                                     :on-change (fn [e] (rf/dispatch [:update-linear-b1! (-> e .-target .-value)]))}]])
(defn b0-input []
  [:div.column.is-half
   [:label.label.has-text-danger-light {:for "b0"} "b0: "]
   [:input#b0.input.is-transparent.has-text-warning {:type "text"
                                                     :value @(rf/subscribe [::subs/linear-b0])
                                                     :placeholder "b0"
                                                     :on-change (fn [e] (rf/dispatch [:update-linear-b0! (-> e .-target .-value)]))}]])