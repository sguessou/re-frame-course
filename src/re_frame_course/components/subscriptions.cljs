(ns re-frame-course.components.subscriptions
  (:require [secretary.core :as s]
            [reagent.core :as reagent]
            [clojure.string :as str]
            [re-frame.core :as rf]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]
            [re-frame-course.components.hiccup-panel :refer [vehicles]]))

(defonce my-vehicles (zipmap (range) (take 20 vehicles)))

(rf/reg-event-db
 :initialize-vehicles
 (fn [db]
   (assoc db :vehicles my-vehicles)))

(rf/reg-event-db
 :like
 (fn [db [_ id]]
   (update-in db [:user :likes] (fnil conj #{}) id)))

(rf/reg-event-db
 :unlike
 (fn [db [_ id]]
   (update-in db [:user :likes] disj id)))

(rf/reg-sub
 :vehicles
 (fn [db]
   (:vehicles db)))

(rf/reg-sub
 :likes
 (fn [db]
   (get-in db [:user :likes] #{})))

(defn vehicle-component [id vehicle]
  (let [likes @(rf/subscribe [:likes])]
    [:div {:style {:display :inline-block
                   :width 80}}
     [:img {:src (:image vehicle)
            :style {:max-width "100%"}}]
     [:a {:on-click (fn [e]
                      (.preventDefault e)
                      (if (contains? likes id)
                        (rf/dispatch [:unlike id])
                        (rf/dispatch [:like id])))
          :href "#"
          :style {:color (if (contains? likes id)
                           :red
                           :grey)
                  :text-decoration :none}}
      "♥"]]))

(defn subscriptions-panel []
  [:div
   [:h1 "Subscriptions"]
   [:div
    (doall
     (for [[id vehicle] @(rf/subscribe [:vehicles])]
       [:span {:key id}
        [vehicle-component id vehicle]]))]
   [:div
    (pr-str @(rf/subscribe [:likes]))]
   [:hr]
   [:a {:href "#/"} "Back"]
   ])

(rf/dispatch [:initialize-vehicles])
(comment

  my-vehicles
 )
