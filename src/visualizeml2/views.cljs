(ns visualizeml2.views
  (:require
   [re-frame.core :as rf]
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
;; Accept color as a prop for text and brackets
(defn column-vector-svg [values & {:keys [color] :or {color "#A7DCFF"}}]
  (let [font-size 24
        spacing 32
        n (count values)
        width 120
        height (* spacing n)
        x-left 10
        x-right 90
        y-top font-size
        y-bottom (+ y-top (* spacing (dec n)))]
    [:svg {:width width :height (+ height 40)}
     ;; Left bracket
     [:line {:x1 x-left :y1 y-top :x2 x-left :y2 y-bottom :stroke color :stroke-width 3}]
     [:line {:x1 x-left :y1 y-top :x2 (+ x-left 15) :y2 y-top :stroke color :stroke-width 3}]
     [:line {:x1 x-left :y1 y-bottom :x2 (+ x-left 15) :y2 y-bottom :stroke color :stroke-width 3}]
     ;; Right bracket
     [:line {:x1 x-right :y1 y-top :x2 x-right :y2 y-bottom :stroke color :stroke-width 3}]
     [:line {:x1 x-right :y1 y-top :x2 (- x-right 15) :y2 y-top :stroke color :stroke-width 3}]
     [:line {:x1 x-right :y1 y-bottom :x2 (- x-right 15) :y2 y-bottom :stroke color :stroke-width 3}]
     ;; Numbers
     (for [[i v] (map-indexed vector values)]
       [:text {:x 40
               :y (+ y-top 10 (* i spacing))
               :font-size font-size
               :fill color
               :font-family "Menlo, monospace"}
        v])]))

;; Accept color as a prop for label and vector
;; Style label as bold and italics
(defn column-vector-with-label [values label & {:keys [color] :or {color "#A7DCFF"}}]
  [:div {:style {:display "flex"
                 :align-items "center"
                 :margin "0 8px"}}
   [:span {:style {:font-family "Menlo, monospace"
                   :font-size "24px"
                   :color color
                   :margin-right "12px"
                   :font-weight "bold"
                   :font-style "italic"}}
    label]
   [column-vector-svg values :color color]])
;; Refactor: allow rendering two column vectors with labels side by side
;; Style as white and flush right
(defn column-vectors-side-by-side [x-values y-values]
  [:div {:style {:display "flex"
                 :gap "0px"
                 :justify-content "flex-end"}}
   [column-vector-with-label x-values "x =" :color "white"]
   [column-vector-with-label y-values "y =" :color "white"]])


(defn linear-regression []
  [:div
   [:h1.is-family-monospace.has-text-centered.is-size-3.has-text-info-75	 "Linear Regression"]
   [:div.section.pb-0 ;; First section (Line chart + data + buttons)
    [:div.columns ;; container for columns
     [:div.column.is-three-fifths.debug  ;; left column
      [charts/scatter-plot
       @(rf/subscribe [::subs/linear-data])
       @(rf/subscribe [::subs/show-estimate-line])
       @(rf/subscribe [::subs/show-linear-loss-eqn])]]
     [:div.column ;; right column
      [:button.button.is-outlined.is-info {:on-click
                                           #(rf/dispatch [:update-linear-data-and-params! (generate/generate-data 10)])} "Generate-data"]
      [column-vectors-side-by-side [1 2 3 4 5 6 7 8 9 10] [1 2 3 4 5 6 7 8 9 10]]
      [:button.button.is-outlined.is-info {:on-click
                                           #(rf/dispatch [:toggle-estimate-line])}
       "Draw rough line of best fit"]]]
    ]
   [:div.section.pt-0.debug ;; Second Section (Params + Loss)
    [:div.columns
     [:div.column.is-3.debug.pl-6 ;; Left Column of text, param input, and buttons 
      [:div.columns ;; Param Inputs
       [:div.column.is-half
        [:label.label.has-text-danger {:for "b1"} "b1: "]
        [:input#b1.input.is-transparent.has-text-warning {:type "text"
                          :value @(rf/subscribe [::subs/linear-b1])
                          :placeholder "b0"
                          :on-change (fn [e] (rf/dispatch [:update-linear-b1! (-> e .-target .-value)]))}]
        ]
       [:div.column.is-half
        [:label.label.has-text-danger-light {:for "b0"} "b0: "]
        [:input#b0.input.is-transparent.has-text-warning {:type "text"
                          :value @(rf/subscribe [::subs/linear-b0])
                          :placeholder "b0"
                          :on-change (fn [e] (rf/dispatch [:update-linear-b0! (-> e .-target .-value)]))}]]
       
       ]
      [:button.button.is-outlined.is-info {:on-click
                                           #(rf/dispatch [:toggle-linear-eqn])}
       "Calculate Loss"]
      ]
     [:div.column.is-4.debug.pt-5
      [linear-loss-eqn-css "L = \\sum_{i=1}^{n} (y_i - \\hat{y}_i)^2"]
      (when @(rf/subscribe [::subs/show-linear-loss-eqn])
        [:div.is-size-2.has-text-white.is-family-monospace.pl-6.pt-2 (str "= " (.toFixed (:loss @(rf/subscribe [::subs/linear-deps])) 2))])
      ]
     [:div.column.is-one-third.debug
      (when @(rf/subscribe [::subs/show-linear-loss-eqn])
        [:div [:button.button.is-outlined.is-info.is-medium.is-fullwidth {:on-click #(rf/dispatch [:show-workings])}
               "Optimize Loss!"]])]
     ]
    ]
   ])






;; [:p (:fn-text @(rf/subscribe [::subs/linear-deps]))]




;; [optimize-loss-workings]
(defn main-panel [] 
  [:div.has-background-black 
   [:div.container.pt-5	
         [linear-regression]]])
