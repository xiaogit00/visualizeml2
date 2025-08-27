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

(rf/reg-sub
 ::linear-deps
 (fn [db]
   (when-let [b0 (:linear-b0 db)]
     (let [b1 (:linear-b1 db)
           y (map :y (:linear-data db))
           y_pred (map #(+ (* % b1) b0) y)
           loss (reduce + (map #(Math/pow (- %2 %1) 2) y y_pred))
           fn-text (utils/fn->pretty-str '(fn [x] (+ (* b1 x) b0)) {'b1 b1 'b0 (goog.string/format "%.4f" b0)})
           optimal-params {:b0 1 :b1 2}] ;; this is a placeholder, function will be filled in soon.
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