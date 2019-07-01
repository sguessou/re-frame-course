(ns re-frame-course.components.atoms
  (:require [secretary.core :as s]
            [reagent.core :as reagent]
            [clojure.string :as str]
            [re-frame.core :as rf]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]))

(defonce my-state (reagent/atom {}))

(defn atoms-panel []
  [:div
   [:h1 "Atoms"]
   (pr-str @my-state)
   [:hr]
   [:a {:href "#/"} "Back"]
   ])

(comment
  (js/console.log "hello")
  (reset! my-state {})
  (reset! my-state {:name "Saad"})
  
  (reset! my-state 0)
  (swap! my-state inc)
  (swap! my-state assoc :age 47)
  (swap! my-state update :age inc)
  
  (swap! my-state assoc-in [:people 1] {:name "Saad" :age 47})
  (swap! my-state update-in [:people 1 :age] inc)
  )
