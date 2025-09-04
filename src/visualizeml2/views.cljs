(ns visualizeml2.views
  (:require
   [re-frame.core :as rf]
   [visualizeml2.subs :as subs]
   [visualizeml2.math.generate :as generate]
   [visualizeml2.charts :as charts]
   [visualizeml2.math.utils :as utils]
   [visualizeml2.math.equations :as equations]
   [visualizeml2.components.svg :as svg]
   [visualizeml2.components.buttons :as buttons]
   [visualizeml2.components.inputs :as inputs]
   [visualizeml2.components.linear-reg :as linear-reg]
   [visualizeml2.components.katex :as katex]
   ))



(defn first-section []
  ;; CONTAINS THE FIRST PANE: I.E. SCATTER PLOT, BUTTONS, AND VECTOR RENDERS
  [:div.columns ;; container for columns
   ;; LEFT COLUMN
   [:div.column.is-three-fifths.debug
    [charts/scatter-plot
     @(rf/subscribe [::subs/linear-data])
     @(rf/subscribe [::subs/show-estimate-line])
     @(rf/subscribe [::subs/show-linear-loss-eqn])
     (:fn-text @(rf/subscribe [::subs/linear-deps]))]]
   ;; RIGHT COLUMN
   [:div.column
    [buttons/generate-data-button]
    (when @(rf/subscribe [::subs/linear-b0])
      [:<>
       [svg/column-vectors-side-by-side [1 2 3 4 5 6 7 8 9] (vec (map :y @(rf/subscribe [::subs/linear-data])))]
       [buttons/estimate-best-fit-button]])]])


(defn linear-regression-page []
  [:div
   [:h1.is-family-monospace.has-text-centered.is-size-3.has-text-info-75	 "Linear Regression"]
   [:div.section.pb-0 ;; First section (Line chart + data + buttons)
    [first-section]
    [:div.section.pt-0.debug ;; Second Section (Params + Loss)
     [:div.columns
      [:div.column.is-3.debug.pl-6 ;; Left Column of text, param input, and buttons 
       [:div.columns ;; Param Inputs
        [inputs/b1-input]
        [inputs/b0-input]
        ]
       [buttons/calculate-loss-button]
       ]
      [equations/loss-equation]
      
      [:div.column.is-one-third.debug
       [buttons/optimize-loss-button]
       [linear-reg/b0b1-display]
       ]
      ]
     [linear-reg/explanation-text]
     [linear-reg/workings]

     ]]])

(defn main-panel [] 
  [:div.has-background-black 
   [:div.container.pt-5	
         [linear-regression-page]]])
