(ns visualizeml2.components.katex
  (:require
   ["katex" :as katex-lib]))

(defn katex [latex-str]
  [:span {:dangerouslySetInnerHTML
          {:__html (katex-lib/renderToString latex-str)}}])