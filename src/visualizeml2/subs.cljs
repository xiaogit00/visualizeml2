(ns visualizeml2.subs
  (:require
   [re-frame.core :as rf]
   [visualizeml2.math.utils :as utils]
   [goog.string :as gstring]))


(rf/reg-sub
 ::linear-data
 (fn [db]
   (:linear-data db)))

(rf/reg-sub
 ::show-estimate-line
 (fn [db]
   (:show-estimate-line db)))


(rf/reg-sub
 ::linear-b0
 (fn [db]
   (:linear-b0 db)))

(rf/reg-sub
 ::linear-b1
 (fn [db]
   (:linear-b1 db)))

(defn calculate-optimal-b1 
  "This implements the b1 eqn: sum((x-x_bar)(y-y_bar))/sum(x-x_bar)^2"
  [xs ys x_bar y_bar]
  (/
   (reduce +
           (map
            (fn [x y] (* (- x x_bar) (- y y_bar)))
            xs ys))
   (reduce +
           (map
            #(* (- % x_bar) (- % x_bar))
            xs))) 
  )

(rf/reg-sub
 ::linear-deps
 (fn [db]
   (when-let [b0 (js/Number (:linear-b0 db))]
     (let [b1 (:linear-b1 db)
           linear-data (:linear-data db)
           ys (map :y linear-data)
           xs (map :x linear-data)
           y_pred (map #(+ (* % b1) b0) ys)
           loss (reduce + (map #(Math/pow (- %2 %1) 2) ys y_pred))
           fn-text (utils/fn->pretty-str '(fn [x] (+ (* b1 x) b0)) {'b1 b1 'b0 (goog.string/format "%.4f" b0)})
           y_bar (/ (reduce + ys) (count ys))
           x_bar (/ (reduce + xs) (count xs))
           optimal-b1 (calculate-optimal-b1 xs ys x_bar y_bar)
           optimal-b0 (- y_bar (* optimal-b1 x_bar))
           optimal-params {:b0 optimal-b0 :b1 optimal-b1}] ;; this is a placeholder, function will be filled in soon.
       {:loss loss
        :fn-text fn-text
        :optimal-params optimal-params})
     )
   ))

(rf/reg-sub
 ::show-linear-loss-eqn
 (fn [db]
   (:show-linear-loss-eqn db)))

(rf/reg-sub
 ::show-workings
 (fn [db]
   (:show-workings db)))

(def sampleY [{:x 5, :y 12} {:x 3, :y 13}])
(def ys (map :y sampleY))
(def xs (map :x sampleY))
(def y_bar (/ (reduce + ys) (count ys)))
(def x_bar (/ (reduce + xs) (count xs)))
x_bar
y_bar





(/ (reduce + (map #(:y %) sampleY)) (count sampleY))
