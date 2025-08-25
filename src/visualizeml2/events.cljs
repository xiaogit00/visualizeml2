(ns visualizeml2.events
  (:require
   [re-frame.core :as rf]
   [visualizeml2.db :as db]
   [visualizeml2.math.utils :as utils]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   [goog.string :as gstring]
   ))

(rf/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
   db/default-db))

(rf/reg-event-db 
 :generate-data-and-params
 (fn [db [_ linear-data]]
   (let [point1 (get linear-data 2)
         point2 (utils/get-second-last linear-data)
         b1 (utils/calculate-b1 point1 point2)
         b0 (rand 3)
         fn-text (utils/fn->pretty-str '(fn [x] (+ (* b1 x) b0)) {'b1 b1 'b0 (goog.string/format "%.4f" b0)})]
     (assoc db 
            :linear-data linear-data
            :linear-b0 b0
            :linear-b1 b1
            :linear-fn-text fn-text)
     )))

(rf/reg-event-db
 :toggle-estimate-line
 (fn [db _]
   (update db :show-estimate-line not)))

(rf/reg-event-db
 :update-linear-b1
  (fn [db [_ b1]]
   (assoc db :linear-b1 b1)))

(rf/reg-event-db
 :update-linear-b0
 (fn [db [_ b0]]
   (assoc db :linear-b0 b0)))

(rf/reg-event-db
 :update-linear-fn-text
 (fn [db [_ fn-text]]
   (assoc db :linear-fn-text fn-text)))

(rf/reg-event-db
 :render-loss
 (fn [db _]
   (assoc db
          :show-linear-loss-eqn true
          :linear-loss 5)
   ))