(ns re-frame-course.routes
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.history.Html5History)
  (:require [secretary.core :as s]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [re-frame-course.state :refer [app-state]]
            [re-frame-course.components.forms :refer [components-panel]]
            [re-frame-course.components.hiccup-panel :refer [hiccup-panel]]
            [re-frame-course.components.atoms :refer [atoms-panel]]
            [re-frame-course.components.events :refer [events-panel]]
            [re-frame-course.components.subscriptions :refer [subscriptions-panel]]
            ))

(defn hook-browser-navigation! []
  (doto (Html5History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (s/dispatch! (.-token event))))
    (.setEnabled true)))

(defn app-routes []
  (s/set-config! :prefix "#")

  (defroute "/" []
    (swap! app-state assoc :page :home))

  (defroute "/components" []
    (swap! app-state assoc :page :components))

  (defroute "/hiccup-panel" []
    (swap! app-state assoc :page :hiccup-panel))

  (defroute "/atoms-panel" []
    (swap! app-state assoc :page :atoms-panel))

  (defroute "/events-panel" []
    (swap! app-state assoc :page :events-panel))

  (defroute "/subscriptions-panel" []
    (swap! app-state assoc :page :subscriptions-panel))

  (hook-browser-navigation!))

(defn home []
  [:div
   [:h1 "Hiccup"]
   [:hr]
   [:p [:a {:href "#/components"} "Components"]]
   [:p [:a {:href "#/hiccup-panel"} "Hiccup"]]
   [:p [:a {:href "#/atoms-panel"} "Atoms"]]
   [:p [:a {:href "#/events-panel"} "Events"]]
   [:p [:a {:href "#/subscriptions-panel"} "Subscriptions"]]])

(defn components []
  [components-panel])

(defn hiccup-component []
  [hiccup-panel])

(defn atoms-component []
  [atoms-panel])

(defn events-component []
  [events-panel])

(defn subscriptions-component []
  [subscriptions-panel])

(defmulti current-page #(@app-state :page))
(defmethod current-page :home []
  [home])
(defmethod current-page :components []
  [components])
(defmethod current-page :hiccup-panel []
  [hiccup-component])
(defmethod current-page :atoms-panel []
  [atoms-component])
(defmethod current-page :events-panel []
  [events-component])
(defmethod current-page :subscriptions-panel []
  [subscriptions-component])
(defmethod current-page :default []
  [:div "default"])
