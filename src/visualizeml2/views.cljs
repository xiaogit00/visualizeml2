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
   ))



(defn first-section []
  ;; CONTAINS THE FIRST PANE: I.E. SCATTER PLOT, BUTTONS, AND VECTOR RENDERS
  [:div.columns ;; container for columns
   ;; LEFT COLUMN
   [:div.column.is-three-fifths.debug
    [charts/scatter-plot
     @(rf/subscribe [::subs/linear-data])
     @(rf/subscribe [::subs/show-estimate-line])
     @(rf/subscribe [::subs/show-linear-loss-eqn])]]
   ;; RIGHT COLUMN
   [:div.column
    [buttons/generate-data-button]
    [svg/column-vectors-side-by-side [1 2 3 4 5 6 7 8 9 10] [1 2 3 4 5 6 7 8 9 10]]
    [buttons/estimate-best-fit-button]]])

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
      ]
     ]
    ]]])


;; [:p (:fn-text @(rf/subscribe [::subs/linear-deps]))]




;; [optimize-loss-workings]
(defn main-panel [] 
  [:div.has-background-black 
   [:div.container.pt-5	
         [linear-regression-page]]])
