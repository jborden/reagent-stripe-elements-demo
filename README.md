# reagent-stripe-elements-demo

Based on

https://github.com/stripe/react-stripe-elements
https://jsfiddle.net/g9rm5qkt/

Live demo 

https://jborden.github.com/reagent-stripe-elements-demo

Accompanying blog post

https://jborden.github.io/2018/02/18/reagent-stripe-elements

# Dev Setup

run 
```
$ lein figwheel
```

or 
```
$ rlwrap lein figwheel
```

Connect to http://localhost:3449/ 

Edit resources/public/index.html

# Production Setup

run
```
$ lein cljsbuild once production
```

Start the node.js server

```
$ node server.js
```

Connect to http://localhost:8080
Copyright © 2018 James Borden

Distributed under the MIT License
