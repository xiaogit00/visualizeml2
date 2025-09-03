(ns visualizeml2.math.utils
  (:require [clojure.string :as str]))

(defn get-second-last [v]
  (when (>= (count v) 2)
    (nth v (- (count v) 2))))

(defn calculate-b1
  "Calculates the gradient between 2 points; both points are maps of coordinates"
  [point1 point2]
  (let [{x1 :x
         y1 :y} point1
        {x2 :x
         y2 :y} point2
        rise (- y2 y1)
        run (- x2 x1)]
    (/ rise run)))

(defn expr->str [expr env]
  (cond
    ;; numbers
    (number? expr) (str expr)

    ;; symbols
    (symbol? expr) (if-let [v (get env expr)]
                     (str v)
                     (str expr))
    ;; sequences
    (seq? expr)
    (let [[op & args] expr]
      (case op
        + (str/join " + " (map #(expr->str % env) args))
        - (str/join " - " (map #(expr->str % env) args))
        * (str/join "*"   (map #(expr->str % env) args))
        / (str/join "/"   (map #(expr->str % env) args))
        ;; default: function-style
        (str (name op) "(" (str/join ", " (map #(expr->str % env) args)) ")")))
    :else
    (str expr)))

(defn fn->pretty-str [fn-form env]
  (let [[_ [arg] body] fn-form]
    (str "f(" arg ") = " (expr->str body env))))

(defn math-formula
  [latex & [{:keys [display? style class id] :as opts}]]
  (let [wrapped (if display?
                  (str "\\[" latex "\\]")   ;; block math (centered)
                  (str "\\(" latex "\\)"))] ;; inline math
    [:span (cond-> {:dangerouslySetInnerHTML {:__html wrapped}}
             style (assoc :style style)
             class (assoc :class class)
             id    (assoc :id id))]))
