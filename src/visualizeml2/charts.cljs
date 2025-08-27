(ns visualizeml2.charts
  (:require ["recharts" :refer [ScatterChart Scatter XAxis YAxis CartesianGrid Tooltip Legend Line]]
            [visualizeml2.math.generate :as generate]))

(defn scatter-plot [data show-estimate-line]
  [:> ScatterChart
   {:width 600
    :height 400
    :data data
    :margin {:top 20 :right 20 :bottom 20 :left 20}}
   [:defs
    [:marker {:id "arrow"
              :markerWidth 10
              :markerHeight 10
              :refX 10
              :refY 5
              :orient "auto"
              :markerUnits "strokeWidth"}
     [:path {:d "M0,0 L10,5 L0,10"
             :fill "none"
             :stroke "pink"
             :strokeWidth 1}]
     [:marker {:id "arrow-back"
               :markerWidth 10
               :markerHeight 10
               :refX 0
               :refY 5
               :orient "auto"
               :markerUnits "strokeWidth"}
      [:path {:d "M10,0 L0,5 L10,10"
              :fill "none"
              :stroke "pink"
              :strokeWidth 1}]]]]
   [:> CartesianGrid {:strokeDasharray "3 3"}]
   [:> XAxis {:type "number" :dataKey "x" :name "X Value"}]
   [:> YAxis {:type "number" :dataKey "y" :name "Y Value"}]
   [:> Tooltip {:cursor {:strokeDasharray "3 3"}}]
   [:> Legend]
   (when show-estimate-line 
     [:> Line
      {:type "monotone"
       :dataKey "y"
       :data (generate/generate-estimate-line-data)
       :stroke "#8884d8"
       :dot false}]
     )
   [:> Line {:type "linear"
             :dataKey "y"
             :data [{:x 5 :y 1}
                    {:x 5 :y 15}]
             :stroke "pink"
             :dot false
             :isAnimationActive false
             :markerEnd "url(#arrow)"
             :markerStart "url(#arrow-back)"}]
   [:> Scatter
    {:name "Data Points"
     :data data
     :fill "pink"}]])
