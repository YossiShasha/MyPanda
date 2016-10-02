package server;

import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.server.*;
import akka.http.javadsl.server.values.Parameters;
import statistics.Statistics;

public class HttpServer extends HttpApp {

    // A RequestVal is a type-safe representation of some aspect of the request.
    // In this case it represents the `eventType` URI parameter of type String.
    private RequestVal<String> eventType = Parameters.stringValue("type");

    @Override
    public Route createRoute() {
        // This handler generates responses to `/EventType?type=XXX` requests
        Route eventTypeRoute =
                handleWith1(eventType,
                        (ctx, eventType) -> ctx.complete(showEventTypeCounter(eventType))
                );

        // This handler generates responses to `/WordCount` requests
        Route wordCountRoute =
                handleWith(requestContext -> requestContext.complete(showEventsWordCount())
                );

        return
                // here the complete behavior for this server is defined
                route(
                        // only handle GET requests
                        get(
                                // matches the empty path
                                pathSingleSlash().route(
                                        // return a constant string with a certain content type
                                        complete(ContentTypes.TEXT_HTML_UTF8,
                                                "<html><body>Hello BigPanda!</body></html>")
                                ),
                                path("WordCount").route(
                                        // Use word count route
                                        wordCountRoute
                                ),
                                path("EventType").route(
                                        // Use event count route
                                        eventTypeRoute
                                )
                        )
                );

    }

    private String showEventsWordCount() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Events total word count is: ").append(Statistics.INSTANCE.getEventsWordCount());
        return strBuilder.toString();
    }

    private String showEventTypeCounter(String eventType){
        StringBuilder strBuilder = new StringBuilder();
        long counter = Statistics.INSTANCE.getEventTypeCount(eventType);
        strBuilder.append(eventType).append(" event type count is: ").append(counter);
        return strBuilder.toString();
    }
}
