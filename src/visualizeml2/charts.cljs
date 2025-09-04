(ns visualizeml2.charts
  (:require ["recharts" :refer [ScatterChart Scatter XAxis YAxis CartesianGrid Tooltip Legend Line]]
            [visualizeml2.math.generate :as generate]))


(defn residual-line [error-data] [:> Line {:type "linear"
                                 :dataKey "y"
                                 :data error-data
                                 :stroke "#FF6182"
                                 :dot false
                                 }])

(defn scatter-plot [data show-estimate-line show-linear-loss-eqn fn-text]
  [:> ScatterChart
   {:width 720
    :height 480
    :data data
    :margin {:top 4 :right 4 :bottom 4 :left 0}}
   [:text {:x 100 :y 50 :fill "#8884d8" :fontSize 24 :fontFamily "Menlo, monospace"}
    fn-text]
   [:> CartesianGrid {:strokeDasharray "3 3"}]
   [:> XAxis {:type "number" :dataKey "x" :name "X Value" :stroke "#FFDE82" :axisLine true}]
   [:> YAxis {:type "number" :dataKey "y" :name "Y Value" :stroke "#FFDE82" :axisLine true}]
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
   (when show-linear-loss-eqn
     (map (fn [error-data] (residual-line error-data)) (generate/generate-residual-data)))
   [:> Scatter
    {:name "Data Points"
     :data data
     :fill "pink"}]])
