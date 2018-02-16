(ns reagent-stripe-elements-demo.core
  (:require [cljsjs.react-stripe-elements]
            [reagent.core :as r]))

;; based on: https://github.com/stripe/react-stripe-elements
;;           https://jsfiddle.net/g9rm5qkt/

(def default-state {:error-message nil})

(def state (r/atom default-state))

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
     (r/create-class {:display-name "stripe-form"
                      :render
                      (fn [this]
                        [:form {:on-submit (fn [e]
                                             (.preventDefault e)
                                             (.then (this.props.stripe.createToken)
                                                    (fn [payload]
                                                      (.log js/console payload))))
                                :class "StripeForm"}
                         [:label "Card Number"
                          [CardNumberElement {:style element-style}]]
                         [:label "Expiration date"
                          [CardExpiryElement {:style element-style}]]
                         [:label "CVC"
                          [CardCVCElement {:style element-style}]]
                         [:label "Postal code"
                          [PostalCodeElement {:style element-style}]]
                         [:button.ui.primary.button "Pay"]])})))))

(defn ^:export init
  []
  (r/render [:div [:h2 "Card Split-field Form"]
             [StripeProvider {:apiKey stripe-public-key}
              [Elements [StripeForm]]]]
            (.getElementById js/document "app")))

(.log js/console "Hello world!")
