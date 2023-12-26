!function(e,t){"object"==typeof exports&&"object"==typeof module?module.exports=t():"function"==typeof define&&define.amd?define([],t):"object"==typeof exports?exports.ThreeSixty=t():e.ThreeSixty=t()}(self,(()=>(()=>{"use strict";var e={d:(t,i)=>{for(var n in i)e.o(i,n)&&!e.o(t,n)&&Object.defineProperty(t,n,{enumerable:!0,get:i[n]})}};e.g=function(){if("object"==typeof globalThis)return globalThis;try{return this||new Function("return this")()}catch(e){if("object"==typeof window)return window}}(),e.o=(e,t)=>Object.prototype.hasOwnProperty.call(e,t);var t={};function i(e,t){for(var i=0;i<t.length;i++){var n=t[i];n.enumerable=n.enumerable||!1,n.configurable=!0,"value"in n&&(n.writable=!0),Object.defineProperty(e,n.key,n)}}function n(e,t,i){!function(e,t){if(t.has(e))throw new TypeError("Cannot initialize the same private elements twice on an object")}(e,t),t.set(e,i)}function o(e,t){return function(e,t){return t.get?t.get.call(e):t.value}(e,r(e,t,"get"))}function s(e,t,i){return function(e,t,i){if(t.set)t.set.call(e,i);else{if(!t.writable)throw new TypeError("attempted to set read only private field");t.value=i}}(e,r(e,t,"set"),i),i}function r(e,t,i){if(!t.has(e))throw new TypeError("attempted to "+i+" private field on non-instance");return t.get(e)}e.d(t,{default:()=>x});var a=new WeakMap,h=new WeakMap,u=new WeakMap,c=function(){function e(t,i){var r=this;!function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,e),n(this,a,{writable:!0,value:null}),n(this,h,{writable:!0,value:null}),n(this,u,{writable:!0,value:null}),s(this,h,i),s(this,u,{container:{mousedown:function(e){return s(r,a,e.pageX)},touchstart:function(e){return s(r,a,e.touches[0].clientX)},touchend:function(){return s(r,a,null)}},prev:{mousedown:function(e){e.preventDefault(),t.play(!0)},mouseup:function(e){e.preventDefault(),t.stop()},touchstart:function(e){e.preventDefault(),t.prev()}},next:{mousedown:function(e){e.preventDefault(),t.play()},mouseup:function(e){e.preventDefault(),t.stop()},touchstart:function(e){e.preventDefault(),t.next()}},global:{mouseup:function(){return s(r,a,null)},mousemove:function(e){o(r,a)&&Math.abs(o(r,a)-e.pageX)>o(r,h).dragTolerance&&(t.stop(),o(r,a)>e.pageX?t.prev():t.next(),s(r,a,e.pageX))},touchmove:function(e){o(r,a)&&Math.abs(o(r,a)-e.touches[0].clientX)>o(r,h).swipeTolerance&&(t.stop(),o(r,a)>e.touches[0].clientX?t.prev():t.next(),s(r,a,e.touches[0].clientX))},keydown:function(e){[37,39].includes(e.keyCode)&&t.play(37===e.keyCode)},keyup:function(e){[37,39].includes(e.keyCode)&&t.stop()}}}),this._initEvents()}var t,r;return t=e,(r=[{key:"destroy",value:function(){o(this,h).swipeTarget.removeEventListener("mousedown",o(this,u).container.mousedown),o(this,h).swipeTarget.removeEventListener("touchstart",o(this,u).container.touchstart),o(this,h).swipeTarget.removeEventListener("touchend",o(this,u).container.touchend),window.removeEventListener("mouseup",o(this,u).global.mouseup),window.removeEventListener("mousemove",o(this,u).global.mousemove),window.removeEventListener("touchmove",o(this,u).global.touchmove),window.removeEventListener("keydown",o(this,u).global.keydown),window.removeEventListener("keyup",o(this,u).global.keyup),o(this,h).prev&&(o(this,h).prev.removeEventListener("mousedown",o(this,u).prev.mousedown),o(this,h).prev.removeEventListener("mouseup",o(this,u).prev.mouseup),o(this,h).prev.removeEventListener("touchstart",o(this,u).prev.touchstart)),o(this,h).next&&(o(this,h).next.removeEventListener("mousedown",o(this,u).next.mousedown),o(this,h).next.removeEventListener("mouseup",o(this,u).next.mouseup),o(this,h).next.removeEventListener("touchstart",o(this,u).next.touchstart))}},{key:"_initEvents",value:function(){o(this,h).draggable&&(o(this,h).swipeTarget.addEventListener("mousedown",o(this,u).container.mousedown),window.addEventListener("mouseup",o(this,u).global.mouseup),window.addEventListener("mousemove",o(this,u).global.mousemove)),o(this,h).swipeable&&(o(this,h).swipeTarget.addEventListener("touchstart",o(this,u).container.touchstart),o(this,h).swipeTarget.addEventListener("touchend",o(this,u).container.touchend),window.addEventListener("touchmove",o(this,u).global.touchmove)),o(this,h).keys&&(window.addEventListener("keydown",o(this,u).global.keydown),window.addEventListener("keyup",o(this,u).global.keyup)),o(this,h).prev&&(o(this,h).prev.addEventListener("mousedown",o(this,u).prev.mousedown),o(this,h).prev.addEventListener("mouseup",o(this,u).prev.mouseup),o(this,h).prev.addEventListener("touchstart",o(this,u).prev.touchstart)),o(this,h).next&&(o(this,h).next.addEventListener("mousedown",o(this,u).next.mousedown),o(this,h).next.addEventListener("mouseup",o(this,u).next.mouseup),o(this,h).next.addEventListener("touchstart",o(this,u).next.touchstart))}}])&&i(t.prototype,r),Object.defineProperty(t,"prototype",{writable:!1}),e}();const l=c;function p(e,t){for(var i=0;i<t.length;i++){var n=t[i];n.enumerable=n.enumerable||!1,n.configurable=!0,"value"in n&&(n.writable=!0),Object.defineProperty(e,n.key,n)}}function v(e,t,i){!function(e,t){if(t.has(e))throw new TypeError("Cannot initialize the same private elements twice on an object")}(e,t),t.set(e,i)}function d(e,t){return function(e,t){return t.get?t.get.call(e):t.value}(e,f(e,t,"get"))}function w(e,t,i){return function(e,t,i){if(t.set)t.set.call(e,i);else{if(!t.writable)throw new TypeError("attempted to set read only private field");t.value=i}}(e,f(e,t,"set"),i),i}function f(e,t,i){if(!t.has(e))throw new TypeError("attempted to "+i+" private field on non-instance");return t.get(e)}var y=new WeakMap,g=new WeakMap,m=new WeakMap,b=new WeakMap,k=new WeakMap,E=new WeakMap,L=new WeakMap;const x=function(){function t(e,i){!function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,t),v(this,y,{writable:!0,value:null}),v(this,g,{writable:!0,value:0}),v(this,m,{writable:!0,value:null}),v(this,b,{writable:!0,value:!1}),v(this,k,{writable:!0,value:null}),v(this,E,{writable:!0,value:null}),v(this,L,{writable:!0,value:!1}),this.container=e,w(this,y,Object.assign({width:300,height:300,aspectRatio:0,count:0,perRow:0,speed:100,dragTolerance:10,swipeTolerance:10,draggable:!0,swipeable:!0,keys:!0,inverted:!1},i)),d(this,y).swipeTarget=d(this,y).swipeTarget||this.container,w(this,L,!Array.isArray(d(this,y).image)),this.sprite||(d(this,y).count=d(this,y).image.length),Object.freeze(d(this,y)),w(this,E,new l(this,d(this,y))),this._windowResizeListener=this._windowResizeListener.bind(this),this._initContainer(),this.nloops=0}var i,n;return i=t,(n=[{key:"isResponsive",get:function(){return d(this,y).aspectRatio>0}},{key:"containerWidth",get:function(){return this.isResponsive?this.container.clientWidth:d(this,y).width}},{key:"containerHeight",get:function(){return this.isResponsive?this.container.clientWidth*d(this,y).aspectRatio:d(this,y).height}},{key:"index",get:function(){return d(this,g)}},{key:"looping",get:function(){return d(this,b)}},{key:"sprite",get:function(){return d(this,L)}},{key:"next",value:function(){this.goto(d(this,y).inverted?d(this,g)-1:d(this,g)+1)}},{key:"prev",value:function(){this.goto(d(this,y).inverted?d(this,g)+1:d(this,g)-1)}},{key:"goto",value:function(e){w(this,g,(d(this,y).count+e)%d(this,y).count),this._update()}},{key:"play",value:function(e,t){this.looping||(this._loop(e),w(this,b,!0),w(this,k,t),this.nloops=0)}},{key:"stop",value:function(){this.looping&&(e.g.clearTimeout(d(this,m)),w(this,b,!1),w(this,k,null),this.nloops=0)}},{key:"toggle",value:function(e){this.looping?this.stop():this.play(e)}},{key:"destroy",value:function(){this.stop(),d(this,E).destroy(),this.container.style.width="",this.container.style.height="",this.container.style.backgroundImage="",this.container.style.backgroundPositionX="",this.container.style.backgroundPositionY="",this.container.style.backgroundSize="",this.isResponsive&&window.removeEventListener("resize",this._windowResizeListener)}},{key:"_loop",value:function(t){var i=this;t?this.prev():this.next(),0===d(this,g)&&(this.nloops+=1,d(this,k)&&this.nloops>=d(this,k))?this.stop():w(this,m,e.g.setTimeout((function(){i._loop(t)}),d(this,y).speed))}},{key:"_update",value:function(){this.sprite?(this.container.style.backgroundPositionX=-d(this,g)%d(this,y).perRow*this.containerWidth+"px",this.container.style.backgroundPositionY=-Math.floor(d(this,g)/d(this,y).perRow)*this.containerHeight+"px"):this.container.style.backgroundImage='url("'.concat(d(this,y).image[d(this,g)],'")')}},{key:"_windowResizeListener",value:function(){this.container.style.height=this.containerHeight+"px",this._update()}},{key:"_initContainer",value:function(){if(this.isResponsive||(this.container.style.width=this.containerWidth+"px"),this.container.style.height=this.containerHeight+"px",this.sprite){this.container.style.backgroundImage='url("'.concat(d(this,y).image,'")');var e=d(this,y).perRow,t=Math.ceil(d(this,y).count/d(this,y).perRow);this.container.style.backgroundSize=100*e+"% "+100*t+"%"}this.isResponsive&&window.addEventListener("resize",this._windowResizeListener),this._update()}}])&&p(i.prototype,n),Object.defineProperty(i,"prototype",{writable:!1}),t}();return t.default})()));