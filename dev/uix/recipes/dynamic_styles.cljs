(ns uix.recipes.dynamic-styles
  (:require [uix.core.alpha :as uix]
            [cljsjs.emotion]))

(defonce _init-css-attr-transform
  (do
    (uix/add-transform-fn
      (fn [attrs]
        (if-not (contains? attrs :css)
          attrs
          (let [classes (:class attrs)
                css (:css attrs)
                class (->> (clj->js css)
                           js/emotion.css
                           (str classes " "))]
            (-> attrs
                (dissoc :css)
                (assoc :class class))))))
    0))

(defn recipe []
  (let [border-color (uix/state "#000")]
    [:<>
     [:div "Change border color (red, blue, etc.)"]
     [:input {:value @border-color
              :on-change #(reset! border-color (.. % -target -value))
              :css {:border-width 3
                    :border-color @border-color
                    :border-style :solid
                    :border-radius 5
                    :padding "4px 12px"
                    :font-size 14
                    :outline :none}}]]))
