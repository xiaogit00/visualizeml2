(ns visualizeml2.math.generate
  (:require [visualizeml2.math.utils :as utils]
            [goog.string :as gstring]
            [re-frame.core :as rf]
            [visualizeml2.subs :as subs]))

(defn f [x]
  (-> x
      (* (rand-int 3))
      (+ (rand-int 3))))


(defn generate-data 
  "Generates random x and y values for linear regression"
  [num-inputs]
  (let [xs (vec (range 1 num-inputs))
        ys (vec (map f xs))]
    (mapv (fn [x y] {:x x :y y}) xs ys)))
;; (get-second-last (generate-data 10))

(generate-data 10)


(defn generate-estimate-line-data
  "Generates estimate line given dataset generated from generate-data)"
  []
  (let [data @(rf/subscribe [::subs/linear-data])
        b1 (js/Number @(rf/subscribe [::subs/linear-b1]))
        b0 (js/Number @(rf/subscribe [::subs/linear-b0]))
        g (fn [x] (+ (* b1 x) b0))]
    (map #(assoc % :y (g (:x %))) data))
  )
;; (def mydata [{:x 1, :y 1} {:x 2, :y 6} {:x 3, :y 5} {:x 4, :y 9} {:x 5, :y 12} {:x 6, :y 7} {:x 7, :y 2} {:x 8, :y 2} {:x 9, :y 19}])
;; (generate-estimate-line-data mydata )