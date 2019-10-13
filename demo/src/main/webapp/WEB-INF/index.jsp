<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
    <head>
        <script src="https://jsonresume.org/cdn-cgi/apps/head/QEWNwoPSl2O5LqMXvi595gsiMPg.js"></script>
        <link rel="dns-prefetch" href="//fonts.googleapis.com" />
        <link rel="dns-prefetch" href="//cdnjs.cloudflare.com" />
        <link rel="dns-prefetch" href="//s7.addthis.com" />
        <link rel="dns-prefetch" href="//static.getclicky.com" />
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="description" content="JSON Resume is a community driven open source initiative to create a JSON based standard for resumes.">
        <title> JSON Resume </title>
        <link rel="shortcut icon" href="https://jsonresume.org/favicon.png">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Ubuntu:400,500,700" defer>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato:300,500,700" defer>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css"
            integrity="sha256-AIodEDkC8V/bHBkfyxzolUMw57jeQ9CauwhVW6YJ9CA=" crossorigin="anonymous" defer />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css"
            integrity="sha256-916EbMg70RQy9LHiGkXzG8hSg9EdNy97GazNG/aiY1w=" crossorigin="anonymous" defer />
        <link rel="stylesheet" href="https://jsonresume.org/css/style.css">
        <link rel="canonical" href="https://jsonresume.org/">
        <!--[if lt IE 9]>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"
            integrity="sha256-3Jy/GbSLrg0o9y5Z5n1uw0qxZECH7C6OQpVBgNFYa0g=" crossorigin="anonymous" defer></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js" integrity="sha256-g6iAfvZp+nDQ2TdTR/VVKJf3bGro4ub5fvWSWVRi2NE=" crossorigin="anonymous" defer></script>
        <![endif]-->
        <script src="https://s7.addthis.com/js/300/addthis_widget.js#pubid=thomasdavis" defer></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/pusher/2.2.4/pusher.min.js"
            integrity="sha256-RYDmjZ81o61ejmR5kTjL3+kFPEJi9+5o4gdl/Z6CkJk=" crossorigin="anonymous" defer></script>
        <script defer> function countUp(a,b,c,d,e,f){for(var g=0,h=["webkit","moz","ms"],i=0;i
            <h.length&&!window.requestAnimationFrame;++i)window.requestAnimationFrame=window[h[i]+"RequestAnimationFrame"],window.cancelAnimationFrame=window[h[i]+"CancelAnimationFrame"]||window[h[i]+"CancelRequestAnimationFrame"];window.requestAnimationFrame||(window.requestAnimationFrame=function(a){var c=(new Date).getTime(),d=Math.max(0,16-(c-g)),e=window.setTimeout(function(){a(c+d)},d);return g=c+d,e}),window.cancelAnimationFrame||(window.cancelAnimationFrame=function(a){clearTimeout(a)}),this.options=f||{useEasing:!0,useGrouping:!0,separator:",",decimal:"."},""==this.options.separator&&(this.options.useGrouping=!1);var j=this;this.d="string"==typeof a?document.getElementById(a):a,this.startVal=Number(b),this.endVal=Number(c),this.countDown=this.startVal>this.endVal?!0:!1,this.startTime=null,this.timestamp=null,this.remaining=null,this.frameVal=this.startVal,this.rAF=null,this.decimals=Math.max(0,d||0),this.dec=Math.pow(10,this.decimals),this.duration=1e3*e||2e3,this.version=function(){return"1.1.2"},this.easeOutExpo=function(a,b,c,d){return 1024*c*(-Math.pow(2,-10*a/d)+1)/1023+b},this.count=function(a){null===j.startTime&&(j.startTime=a),j.timestamp=a;var b=a-j.startTime;if(j.remaining=j.duration-b,j.options.useEasing)if(j.countDown){var c=j.easeOutExpo(b,0,j.startVal-j.endVal,j.duration);j.frameVal=j.startVal-c}else j.frameVal=j.easeOutExpo(b,j.startVal,j.endVal-j.startVal,j.duration);else if(j.countDown){var c=(j.startVal-j.endVal)*(b/j.duration);j.frameVal=j.startVal-c}else j.frameVal=j.startVal+(j.endVal-j.startVal)*(b/j.duration);j.frameVal=Math.round(j.frameVal*j.dec)/j.dec,j.frameVal=j.countDown?j.frameVal
            	<j.endVal?j.endVal:j.frameVal:j.frameVal>j.endVal?j.endVal:j.frameVal,j.d.innerHTML=j.formatNumber(j.frameVal.toFixed(j.decimals)),b
            		<j.duration?j.rAF=requestAnimationFrame(j.count):null!=j.callback&&j.callback()},this.start=function(a){return j.callback=a,isNaN(j.endVal)||isNaN(j.startVal)?(console.log("countUp error: startVal or endVal is not a number"),j.d.innerHTML="--"):j.rAF=requestAnimationFrame(j.count),!1},this.stop=function(){cancelAnimationFrame(j.rAF)},this.reset=function(){j.startTime=null,j.startVal=b,cancelAnimationFrame(j.rAF),j.d.innerHTML=j.formatNumber(j.startVal.toFixed(j.decimals))},this.resume=function(){j.startTime=null,j.duration=j.remaining,j.startVal=j.frameVal,requestAnimationFrame(j.count)},this.formatNumber=function(a){a+="";var b,c,d,e;if(b=a.split("."),c=b[0],d=b.length>1?j.options.decimal+b[1]:"",e=/(\d+)(\d{3})/,j.options.useGrouping)for(;e.test(c);)c=c.replace(e,"$1"+j.options.separator+"$2");return c+d},j.d.innerHTML=j.formatNumber(j.startVal.toFixed(j.decimals))}

        </script>
    </head>
    <body>
        <div id="main">
            <div id="viewport">
                <aside id="sidebar">
                    <a href="home">Home</a>
                    <a href="all">Themes</a>
                </aside>
                <div class="inner">
                    <nav id="nav">
                        <a href="#" class="lt">
                        <img src="/img/hamburger.png">
                        </a>
                        <div class="container">
                            <div class="row">
                                <div class="col-sm-12">
                                    <a href="home">JSON Resume</a>
                                    <div class="float-right hidden-xs">
                                        <c:if test="${empty sessionScope.loginUser}">
                                            <a href="registration">Register</a>
                                            <a href="login">Log in</a>
                                        </c:if>
                                        <c:if test="${!empty sessionScope.loginUser}">
                                            <a href="product/all">Themes</a>
                                            <a href="product/new">Create resume</a>
                                        </c:if>
                                        <c:if test="${!empty sessionScope.loginUser}">
                                            <a href="product/user">
                                                <c:out value="${sessionScope.loginUser}"/>
                                            </a>
                                        </c:if>
                                        <c:if test="${!empty sessionScope.loginUser}">
                                            <a href="logout" >Log out</a>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </nav>
                    <header id="header" class="yellow promo">
                        <div class="container">
                            <div class="row">
                                <div class="col-sm-12">
                                    <h1>JSON Resume</h1>
                                    <p>The open source initiative to create a JSON-based standard for resumes. For developers, by developers.</p>
                                </div>
                            </div>
                        </div>
                    </header>
                    <div id="start">
                        <div class="container">
                            <div class="row">
                                <div class="feature col-sm-4">
                                    <h2>New to this?</h2>
                                    <p>Create new account to see and create resume</p>
                                    <a href="registration" class="btn">Get started</a>
                                </div>
                                <div class="feature col-sm-4">
                                    <h2>Resume</h2>
                                    <p>Browse our gallery of resume themes made by the community.</p>
                                    <a href="product/all" class="btn">View themes</a>
                                </div>
                                <div class="feature col-sm-4">
                                    <h2>Make new resume</h2>
                                    <p>Themes do not fix you? Create your own resume</p>
                                    <a href="product/new" class="btn">Create theme</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <footer id="footer" class="container"></footer>
                </div>
            </div>
        </div>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"
            integrity="sha256-ZosEbRLbNQzLpnKIkEdrPv7lOy9C27hHQ+Xp8a4MxAQ=" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.17/d3.min.js"
            integrity="sha256-dsOXGNHAo/syFnazt+KTBsCQeRmlcW1XKL0bCK4Baec=" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/2.4.2/lodash.min.js"
            integrity="sha256-rKk2QnJsnOCsuS8oSzkedgInNJbYmA09J0w26nVBpss=" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha256-U5ZEeKfGNOja007MMD3YBI0A3OSZOQbeG6z2f2Y0hu8=" crossorigin="anonymous" defer></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/epoch/0.8.4/js/epoch.min.js"
            integrity="sha256-uKluQW6PgYqUnbaF80MDXVMg5/U2Jkl+mIX2qliGmJc=" crossorigin="anonymous"></script>
        <script defer> (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){ (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o), m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m) })(window,document,'script','//www.google-analytics.com/analytics.js','ga'); ga('create', 'UA-74754546-1', 'auto'); ga('send', 'pageview'); </script>
        <script src="/js/main.js" defer></script>
    </body>
</html>

