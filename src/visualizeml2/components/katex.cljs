(ns visualizeml2.components.katex
  (:require
   ["katex" :as katex]
   [reagent.core :as r]))

(defn katex-component
  [{:keys [equation display-mode class]
    :or   {display-mode false class ""}}]
  (let [node (atom nil)]
    (r/create-class
     {:display-name "katex-component"

      :component-did-mount
      (fn [this]
        (let [{:keys [equation display-mode]} (second (r/argv this))
              el @node]
          (when (and el equation)
            (set! (.-innerHTML el) "")
            (.render katex equation el (clj->js {:throwOnError false
                                                 :displayMode display-mode})))))

      :component-did-update
      (fn [this old-argv]
        (let [old-props (second old-argv)
              new-props (second (r/argv this))
              old-eq    (:equation old-props)
              new-eq    (:equation new-props)
              disp?     (:display-mode new-props false)
              el        @node]
          (when (and el new-eq (not= old-eq new-eq))
            (set! (.-innerHTML el) "")
            (.render katex new-eq el (clj->js {:throwOnError false
                                               :displayMode disp?})))))

      :reagent-render
      (fn [{:keys [class]}]
        [:div {:class class
               :ref   (fn [el] (reset! node el))}])})))