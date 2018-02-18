(ns reagent-stripe-elements-demo.core
  (:require [cljsjs.react-stripe-elements]
            [reagent.core :as r]))

;; based on: https://github.com/stripe/react-stripe-elements
;;           https://jsfiddle.net/g9rm5qkt/

(def stripe-public-key
  (-> (.getElementById js/document "stripe-public-key")
      (.getAttribute "data-stripe-public-key")))

;; Stripe elements
(def Elements (r/adapt-react-class js/ReactStripeElements.Elements))
(def CardCVCElement (r/adapt-react-class js/ReactStripeElements.CardCVCElement))
(def CardElement (r/adapt-react-class js/ReactStripeElements.CardElement))
(def CardExpiryElement (r/adapt-react-class js/ReactStripeElements.CardExpiryElement))
(def CardNumberElement (r/adapt-react-class js/ReactStripeElements.CardNumberElement))
(def PostalCodeElement (r/adapt-react-class js/ReactStripeElements.PostalCodeElement))
(def StripeProvider (r/adapt-react-class js/ReactStripeElements.StripeProvider))

(def element-style {:base {:color "#424770"
                           :letterSpacing "0.025em"
                           :fontFamily "Source Code Pro, Menlo, monospace"
                           "::placeholder" {:color "#aab7c4"}}
                    :invalid {:color "#9e2146"}})

(def StripeForm
  (r/adapt-react-class
   (js/ReactStripeElements.injectStripe
    (r/reactify-component
     (r/create-class {:display-name "stripe-reagent-form"
                      :render
                      (fn [this]
                        (let [element-on-change (fn [e]
                                                  (let [e (js->clj e :keywordize-keys true)
                                                        error (:error e)]
                                                    ;; keeping the error for each element in the state atom
                                                    (swap! (r/state-atom this)
                                                           assoc (keyword (:elementType e))
                                                           error)))
                              errors? (fn []
                                        ;; we're only putting errors in the state-atom,
                                        ;; so this should be true only when there are errors
                                        (not (every? nil? (vals @(r/state-atom this)))))]
                          [:form {:on-submit (fn [e]
                                               (.preventDefault e)
                                               ;; make sure there aren't any errors before submitting
                                               (when-not (errors?)
                                                 (.then (this.props.stripe.createToken)
                                                        (fn [payload]
                                                          (.log js/console payload)))))
                                  :class "StripeForm"}
                           ;; In the case where the form elements themselves catch errors, they are
                           ;; displayed below the input.
                           ;; card number
                           [:label "Card Number"
                            [CardNumberElement {:style element-style
                                                :on-change element-on-change}]]
                           [:div {:style {:color "red"}}
                            @(r/cursor (r/state-atom this) [:cardNumber :message])]
                           ;; expiration date
                           [:label "Expiration date"
                            [CardExpiryElement {:style element-style
                                                :on-change element-on-change}]]
                           [:div {:style {:color "red"}}
                            @(r/cursor (r/state-atom this) [:cardExpiry :message])]
                           ;; cvc number
                           [:label "CVC"
                            [CardCVCElement {:style element-style
                                             :on-change element-on-change}]]
                           [:div {:style {:color "red"}}
                            @(r/cursor (r/state-atom this) [:cardCvc :message])]
                           ;; postal code
                           [:label "Postal code"
                            [PostalCodeElement {:style element-style
                                                :on-change element-on-change}]]
                           [:div {:style {:color "red"}}
                            @(r/cursor (r/state-atom this) [:postalCode :message])]
                           [:button {:style (if (errors?)
                                              {:cursor "default"
                                               :opacity ".45"
                                               :pointer-events "none"})} "Pay"]]))})))))

(defn ^:export init
  []
  (r/render [:div [:h2 "Card Split-field Form"]
             [StripeProvider {:apiKey stripe-public-key}
              [Elements [StripeForm]]]]
            (.getElementById js/document "app")))
