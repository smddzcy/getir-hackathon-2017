angular.module("btford.socket-io",[]).provider("socketFactory",function(){"use strict";var n="socket:";this.$get=["$rootScope","$timeout",function(o,t){var e=function(n,o){return o?function(){var e=arguments;t(function(){o.apply(n,e)},0)}:angular.noop};return function(t){t=t||{};var r=t.ioSocket||io.connect(),c=void 0===t.prefix?n:t.prefix,i=t.scope||o,u=function(n,o){r.on(n,o.__ng=e(r,o))},f=function(n,o){r.once(n,o.__ng=e(r,o))},a={on:u,addListener:u,once:f,emit:function(n,o,t){var c=arguments.length-1,t=arguments[c];return"function"==typeof t&&(t=e(r,t),arguments[c]=t),r.emit.apply(r,arguments)},removeListener:function(n,o){return o&&o.__ng&&(arguments[1]=o.__ng),r.removeListener.apply(r,arguments)},removeAllListeners:function(){return r.removeAllListeners.apply(r,arguments)},disconnect:function(n){return r.disconnect(n)},connect:function(){return r.connect()},forward:function(n,o){n instanceof Array==!1&&(n=[n]),o||(o=i),n.forEach(function(n){var t=c+n,i=e(r,function(){Array.prototype.unshift.call(arguments,t),o.$broadcast.apply(o,arguments)});o.$on("$destroy",function(){r.removeListener(n,i)}),r.on(n,i)})}};return a}}]});