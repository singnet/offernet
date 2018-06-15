package io.singularitynet.offernet

import com.fasterxml.jackson.databind.ObjectMapper;

public class Event {
        private final String eventName;
        private final Object eventObject;

        public Event(String eventName, Object eventObject) {
                this.eventName = eventName;
                this.eventObject = eventObject;
        }

        public toJson() {
    	  ObjectMapper mapper = new ObjectMapper();
		  String json = mapper.writeValueAsString(this);
		  logger.info("Converted object {} to JSON {}", this, json);
		  return json;
        }
}
