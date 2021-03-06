(ns uix.recipes
  (:require [uix.recipes.dynamic-styles :as dynamic-styles]
            [uix.recipes.lazy-loading :as lazy-loading]
            [uix.recipes.state-hook :as state-hook]
            [uix.recipes.global-state :as global-state]
            [uix.core.alpha :as uix]))

(def recipes
  {:dynamic-styles dynamic-styles/recipe
   :lazy-loading lazy-loading/recipe
   :state-hook state-hook/recipe
   :global-state global-state/recipe})

(defn root []
  (let [current-recipe (uix/state :state-hook)]
    [:div {:style {:padding 24}}
     [:div {:style {:margin-bottom 16}}
      [:span "Select recipe: "]
      [:select {:value @current-recipe
                :on-change #(reset! current-recipe (keyword (.. % -target -value)))}
       (for [[k v] recipes]
         ^{:key k}
         [:option {:value k}
          (name k)])]
      [:div [:small "To run server-side rendering recipe: clojure -A:dev -m uix.recipes.server-rendering"]]]
     (when-let [recipe (get recipes @current-recipe)]
       [:div
        [recipe]])]))

(uix/render [root] js/root)

(uix/set-loaded! :recipes)
