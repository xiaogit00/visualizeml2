(ns visualizeml2.charts
  (:require ["recharts" :refer [ScatterChart Scatter XAxis YAxis CartesianGrid Tooltip Legend Line]]
            [visualizeml2.math.generate :as generate]))

(defn scatter-plot [data show-estimate-line]
  [:> ScatterChart
   {:width 600
    :height 400
    :data data
    :margin {:top 20 :right 20 :bottom 20 :left 20}}
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
       :dot false}])
   [:> Scatter
    {:name "Data Points"
     :data data
     :fill "pink"}]])
