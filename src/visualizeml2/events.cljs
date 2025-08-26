(ns visualizeml2.events
  (:require
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   [goog.string :as gstring]
   [re-frame.core :as rf]
   [visualizeml2.db :as db]
   [visualizeml2.math.utils :as utils]))

(rf/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
   db/default-db))

(rf/reg-event-db 
 :update-linear-info!
 (fn [db [_ linear-data]]
   (let [point1 (get linear-data 2)
         point2 (utils/get-second-last linear-data)
         b1 (utils/calculate-b1 point1 point2)
         b0 (rand 3)
         y (map :y linear-data)
         y_pred (map #(+ (* % b1) b0) y)
         loss (reduce + (map #(Math/pow (- %2 %1) 2) y y_pred))
         fn-text (utils/fn->pretty-str '(fn [x] (+ (* b1 x) b0)) {'b1 b1 'b0 (goog.string/format "%.4f" b0)})]
         (println y)
     (assoc db 
            :linear-data linear-data
            :linear-b0 b0
            :linear-b1 b1
            :linear-fn-text fn-text
            :linear-loss loss)
     )))

;; (rf/reg-event-fx
;;  :update-linear-info!
;;  (fn [_ [_ linear-data]]
;;    {:dispatch-n [[:calc-and-update-b0-b1 linear-data]
;;                  [:calc-and-update-linear-deps]]}))
;; (rf/reg-event-db
;;  :calc-and-update-b0-b1
;;  (fn [db [_ linear-data]]
;;    (let [point1 (get linear-data 2)
;;          point2 (utils/get-second-last linear-data)
;;          b1 (utils/calculate-b1 point1 point2)
;;          b0 (rand 3)]
;;      (assoc db
;;             :linear-data linear-data
;;             :linear-b0 b0
;;             :linear-b1 b1
;;             ))))

;; (rf/reg-event-fx
;;  :calc-and-update-linear-deps
;;  (fn [db _]
;;    (let [b0 (:linear-b0 db)
;;          b1 (:linear-b1 db)
;;          linear-data (:linear-data db)
;;          y (map :y linear-data)
;;          y_pred (map #(+ (* % b1) b0) y)
;;          loss (reduce + (map #(Math/pow (- %2 %1) 2) y y_pred))
;;          fn-text (utils/fn->pretty-str '(fn [x] (+ (* b1 x) b0)) {'b1 b1 'b0 (goog.string/format "%.4f" b0)})]
;;          (println b0)
;;      (assoc db
;;             :linear-fn-text fn-text
;;             :linear-loss loss))))

(rf/reg-event-db
 :toggle-estimate-line
 (fn [db _]
   (update db :show-estimate-line not)))

(rf/reg-event-db
 :update-linear-b1
 (fn [db [_ b1]]
   (let [b0 (:linear-b0 db)
         y (map :y (:linear-data db))
         y_pred (map #(+ (* % b1) b0) y)
         loss (reduce + (map #(Math/pow (- %2 %1) 2) y y_pred))
         fn-text (utils/fn->pretty-str '(fn [x] (+ (* b1 x) b0)) {'b1 b1 'b0 (goog.string/format "%.4f" b0)})]
     (assoc db :linear-b1 b1
            :linear-loss loss
            :linear-fn-text fn-text))))

(rf/reg-event-db
 :update-linear-b0
 (fn [db [_ b0]]
   (println b0)
   (let [b1 (:linear-b1 db)
         y (map :y (:linear-data db))
         y_pred (map #(+ (* % b1) b0) y)
         loss (reduce + (map #(Math/pow (- %2 %1) 2) y y_pred))
         fn-text (utils/fn->pretty-str '(fn [x] (+ (* b1 x) b0)) {'b1 b1 'b0 (goog.string/format "%.4f" b0)})]
     (assoc db :linear-b0 b0
            :linear-loss loss
            :linear-fn-text fn-text))))

(rf/reg-event-db
 :update-linear-fn-text
 (fn [db [_ fn-text]]
   (assoc db :linear-fn-text fn-text)))

(rf/reg-event-db
 :toggle-linear-eqn
 (fn [db _]
   (update db :show-linear-loss-eqn not)))
