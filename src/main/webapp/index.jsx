// Declare usage of Grommet components
var Anchor = Grommet.Anchor;
var App = Grommet.App;
var Box = Grommet.Box;
var Button = Grommet.Button;
var Footer = Grommet.Footer;
var Header = Grommet.Header;
var Heading = Grommet.Heading;
var Logo = Grommet.Logo;
var Menu = Grommet.Menu;
var Paragraph = Grommet.Paragraph;
var Sidebar = Grommet.Sidebar;
var Split = Grommet.Split;
var Tab = Grommet.Tab;
var Tabs = Grommet.Tabs;
var Title = Grommet.Title;

// Create a new React component
class Page extends React.Component {
    // constructor
    constructor(props) {
        // always call super(props) to call React.Component constructor
        super(props);

        // declare class state variables
        // state is anything of the React component that will update at some point
        this.state = {

        };
    }

    // Once the component is created and inserted into the page, do the following:
    componentDidMount() {/*
        // base URI for creating the websocket address
        var loc = window.location, new_uri;
        // If page is using securte HTTP, use secure WebSocket, otherwise use plain WebSocket
        if (loc.protocol === "https:") {
            new_uri = "wss:";
        } else {
            new_uri = "ws:";
        }
        // add hostname and server file path to URI
        new_uri += "//" + document.location.host + document.location.pathname;
        // set the websocket path to the endpoint of the java server, linked to @ServerEndpoint in java
        this.ws = new WebSocket(new_uri+'websocket');
        // when the websocket gets a message, call the messageHandler function
        this.ws.onmessage = e => {this.messageHandler(e)};
        // Write an error to the error class variable
        this.ws.onerror = e => this.setState({error: 'WebSocket error'});
        // if the websocket does not close cleanly, set an error to the error class variable
        this.ws.onclose = e => !e.wasClean && this.setState({error: `WebSocket error: ${e.code} ${e.reason}`});*/
    }

    // handle messages from the server
    messageHandler(message) {
        // websocket messages are JSON. The JSON message the server sends is in the data attribute of this message JSON
        // So, parse the JSON message containing the message data
        var jsonMessage = JSON.parse(message.data);
    }

    // Close the websocket before the react component is destroyed
    componentWillUnmount() {
        //this.ws.close();
    }

    // the render method of a react component is what is actually rendered to the client
    // You can use plain HTML and javascript, but here we mostly use Grommet
    render() {
        return (
            <App>
                <Split>
                    <Sidebar colorIndex="accent-2">
                        <Header size='xlarge' justify='between' pad={{ horizontal: 'medium' }}>
                            <Title>
                                <span>Navigate</span>
                            </Title>
                        </Header>
                        <Menu fill={true} primary={true}>
                            <Anchor href="#">Test</Anchor>
                        </Menu>
                        <Footer pad={{ horizontal: 'medium', vertical: 'small' }}>

                        </Footer>
                        </Sidebar>
                    <Box>
                        <Heading align='center'>TripCo</Heading>
                    </Box>
                </Split>
            </App>
        );
    }
}

// Render the React component "Page" into the HTML div with the id content (see index.html)
ReactDOM.render(<Page/>, document.getElementById('content'));