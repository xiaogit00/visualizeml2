(ns visualizeml2.components.svg)

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