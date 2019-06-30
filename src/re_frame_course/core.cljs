(ns ^:figwheel-hooks re-frame-course.core
  (:require [reagent.core :as reagent]
            [re-frame-course.routes :refer [app-routes current-page]]))

(defn mount []
  (app-routes)
  (reagent/render
   [current-page]
   (.getElementById js/document "app")))

(defn ^:export main []
  (mount))

(defn ^:after-load re-render []
  (mount))
