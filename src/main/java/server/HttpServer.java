package server;

import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.server.*;
import akka.http.javadsl.server.values.Parameters;
import statistics.Statistics;

public class HttpServer extends HttpApp {

    private static final String WORDS_COUNT_PREFIX = "Events total word count is: ";

    private static final String EVENT_TYPE_STRING = " event type count is: ";



    // Represents the `eventType` URI parameter of type String.
    private RequestVal<String> eventType = Parameters.stringValue("type");

    @Override
    public Route createRoute() {
        // This handler generates responses to `/EventType?type=XXX` requests
        Route eventTypeRoute =
                handleWith1(eventType,
                        (requestContext, eventType) -> requestContext.complete(showEventTypeCount(eventType))
                );

        // This handler generates responses to `/WordCount` requests
        Route wordCountRoute =
                handleWith(requestContext -> requestContext.complete(showEventsWordCount())
                );

        return
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
                                        // Use event type count route
                                        eventTypeRoute
                                )
                        )
                );

    }

    /**
     * Generates the word count string.
     * @return the word count string.
     */
    private String showEventsWordCount() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(WORDS_COUNT_PREFIX).append(Statistics.INSTANCE.getEventsWordCount());
        return strBuilder.toString();
    }

    /**
     * Generates the given event type count string.
     * @param eventType
     * @return the event type count string.
     */
    private String showEventTypeCount(String eventType){
        StringBuilder strBuilder = new StringBuilder();
        long counter = Statistics.INSTANCE.getEventTypeCount(eventType);
        strBuilder.append(eventType).append(EVENT_TYPE_STRING).append(counter);
        return strBuilder.toString();
    }
}
