(ns re-frame-course.components.validation
  (:require [secretary.core :as s]
            [reagent.core :as reagent]
            [re-frame.core :as rf]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]))

(defn user-registration [defaults]
  (let [state (reagent/atom defaults)]
    (fn []
      [:form {:on-submit (fn [e]
                           (.preventDefault e)
                           (rf/dispatch [:form-submit @state]))}
       [:div
        [:label "First name"
         [:input {:name :first-name}]]]])))

(defn forms-panel []
  [:div
   [user-registration {:first-name "Saad"
                       :email "sguessou@gmail.com"
                       :address-line-1 "Kilterinkulma 2 E 11"
                       :address-line-2 ""
                       :zip-code "01600"}]])

