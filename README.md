# protobufbodyparser

This library allows a Play project to easily parse requests who's body is enocded with Google's [protocol buffers](https://developers.google.com/protocol-buffers/).  It should be as simple as:

```scala
package controllers

import com.mondorad.protobufbodyparser.ProtobufBodyParser
import play.api.mvc.{Action, Controller}

object ExampleController extends Controller{
  def uploadTrainingData = Action(ProtobufBodyParser.parser[SampleMsg]){
    request =>{
	...
    }
  }
}
```

Where SampleMsg is the protobuf message that makes up the body.
