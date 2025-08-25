(ns visualizeml2.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 ::name
 (fn [db]
   (:name db)))

(rf/reg-sub
 ::linear-data
 (fn [db]
   (:linear-data db)))

(rf/reg-sub
 ::show-estimate-line
 (fn [db]
   (:show-estimate-line db)))

(rf/reg-sub
 ::linear-fn-text
 (fn [db]
   (:linear-fn-text db)))

(rf/reg-sub
 ::linear-b0
 (fn [db]
   (:linear-b0 db)))

(rf/reg-sub
 ::linear-b1
 (fn [db]
   (:linear-b1 db)))

(rf/reg-sub
 ::show-linear-loss-eqn
 (fn [db]
   (:show-linear-loss-eqn db)))

(rf/reg-sub
 ::linear-loss
 (fn [db]
   (:linear-loss db)))