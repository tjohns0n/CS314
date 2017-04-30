var Accordion = Grommet.Accordion;
var AccordionPanel = Grommet.AccordionPanel;
var App = Grommet.App;
var Box = Grommet.Box;
var Button = Grommet.Button;
var CheckBox = Grommet.CheckBox;
var Heading = Grommet.Heading;
var Headline = Grommet.Headline;
var Label = Grommet.Label;
var List = Grommet.List;
var ListItem = Grommet.ListItem;
var Menu = Grommet.Menu;
var Paragraph = Grommet.Paragraph;
var Select = Grommet.Select;
var Search = Grommet.Search;
var Split = Grommet.Split;
var Tab = Grommet.Tab;
var Table = Grommet.Table;
var TableRow = Grommet.TableRow;
var Tabs = Grommet.Tabs;
var Topology = Grommet.Topology;
var DocumentUploadIcon = Grommet.Icons.Base.DocumentUpload;
var DocumentDownloadIcon = Grommet.Icons.Base.DocumentDownload;
var GlobeIcon = Grommet.Icons.Base.Globe;
var RefreshIcon = Grommet.Icons.Base.Refresh;

// airport object
function Airport (id, name, country, continent, type) {
	this.id = id;
	this.name = name;
  this.country = country;
  this.continent = continent;
  this.type = type;
}

// list of searchable airports
var airports = [];
airports.push(new Airport(1, "Hartsfield Jackson Atlanta International", "United States", "America", "large_airports"));
airports.push(new Airport(2, "Beijing Capital International", "China", "Asia", "large_airports"));
airports.push(new Airport(3, "Dubai International", "United Arab Emirates", "Unknown", "closed"));

var selectedAirports = [];
selectedAirports.push(new Airport(1, "Hartsfield Jackson Atlanta International", "United States", "America", "large_airports"));
selectedAirports.push(new Airport(2, "Beijing Capital International", "China", "Asia", "large_airports"));
selectedAirports.push(new Airport(3, "Dubai International", "United Arab Emirates", "Unknown", "closed"));

var countries = [];
countries.push("United States");
countries.push("China");

var types = [];
types.push("large_airports");
types.push("small_airports");
types.push("closed");

var continents = [];
continents.push("Asia");
continents.push("America");

class TripCo extends React.Component {
  
  constructor(props) {
      super(props);
		
      this.state = {
         error: null,
         data: airports,
         word: "hello"
      }

      this.updateState = this.updateState.bind(this);
   }
     // Check the airport names and countries to see if there are matches 
	// Push them to the searchResults 

   updateState(query) {
     if (query == "refresh"){this.setState({data: airports});}
     else {
      var searchResults = airports.filter(function(obj) { 
            if (obj.name.includes(query) || 
                obj.country.includes(query) ||
                obj.continent.includes(query) ||
                obj.type.includes(query))
                return obj;
			});
      this.setState({data: searchResults});
     }
     
     console.log("[App] Table refreshes value " + query);
   }

  render () {
    return (
      
        <Box id='screen' pad='medium'>
        <Headline align='center' size='medium'>TripCo Online</Headline>
        <Tabs>
          <Tab title="Plan">
            <Box id="PlanBox" full='false'>
              <App>
                <Box id='Search' margin='medium' full='true' pad='small'>
                  <MySearch myDataProp = {this.state.word} 
                    updateStateProp = {this.updateState}></MySearch>
                </Box>
                <MySelectionTable data={this.state.data}
                  updateStateProp = {this.updateState}/>
              </App>
            </Box>
          </Tab>
          
          <Tab title="Selections">
            <App>
              <MySelectedTable data={selectedAirports}/>
            </App>
          </Tab>

          <Tab title="Itinerary">
            <Box id="mapBox" full='true' margin='large'>
            </Box>  
          </Tab>

          <Tab title="TravelMap">
            <Box id="mapBox" full='true' margin='large'>
            </Box>  
          </Tab>
        </Tabs>
        
        </Box>
      
    );
  }
};

class MySelectionTable extends React.Component {
  constructor(props) {
      super(props);
      this.state = {
         Country: countries,
         Continent: continents,
         Type: types
      }
      this.handleChange = this.handleChange.bind(this);
      this.refreshItems = this.refreshItems.bind(this);
  }

  handleChange(value){
    console.log("[MySelectionTable] get selected value:" + value);
    this.props.updateStateProp(value);
  }

  refreshItems(){
    this.props.updateStateProp("refresh")
  }

  render() {
    return (
      <Box id="TripPreview" align='center' full='true'>
        <Table >
          <thead><tr><th width='10%'>id</th>
            <th width='35%'> <Button icon={<RefreshIcon />} plain={true} onClick={this.refreshItems}/> Airport </th>
            <th width='20%'><Menu responsive={true} label='Country' size='small' closeOnClick={false}>
              {this.state.Country.map((one, i) => <Myset key = {i} data = {one} filter={this.handleChange}/>)}</Menu></th>
            <th width='20%'><Menu responsive={true} label='Continent' size='small' closeOnClick={false}>
              {this.state.Continent.map((one, i) => <Myset key = {i} data = {one} filter={this.handleChange}/>)}</Menu></th>
            <th width='20%'><Menu responsive={true} label='Type' size='small' closeOnClick={false}>
              {this.state.Type.map((one, i) => <Myset key = {i} data = {one} filter={this.handleChange}/>)}</Menu></th>
            </tr></thead>
          <tbody>
            {this.props.data.map((one, i) => <MyTableRow key = {i} data = {one} />)}
          </tbody>
        </Table>
      </Box>
    );
  }
}

class Myset extends React.Component {
  constructor(props) {
      super(props);
  }

  clicked(item, event) {
    event.preventDefault()
    this.props.filter(item);
  }

  render(){
    return(
      <Button label={this.props.data} onClick={this.clicked.bind(this, this.props.data)} plain={true} />
    );
  }
}

class MySelectedTable extends React.Component {
  constructor(props) {
      super(props);

      this.uploadFile = this.uploadFile.bind(this);
      this.downloadFile = this.downloadFile.bind(this);
      this.planTrip = this.planTrip.bind(this);
  }

  uploadFile(){

  }

  downloadFile(){

  }

  planTrip(){

  }

  render() {
    return (
      <App>
        <Box id="TripPreview" align='center' full='true' pad='large'>
        <Box direction='row' justify='center'>
          <Button icon={<DocumentUploadIcon />} label='Upload' onClick={this.uploadFile} plain={true} />
          <Button icon={<DocumentDownloadIcon />} label='Download' onClick={this.downloadFile} plain={true} />
          <Button icon={<GlobeIcon />} label='Plan Trip' onClick={this.planTrip} plain={true} />
        </Box>
        
      
        <Paragraph size='xlarge'> View Your Trip </Paragraph>
        <Table >
          <thead><tr><th width='10%'>id</th>
            <th width='35%'> Airport </th>
            <th width='20%'> Country</th>
            <th width='20%'> Continent</th>
            <th width='20%'> Type</th>
            </tr></thead>
          <tbody>
            {this.props.data.map((one, i) => <MySelectedRow key = {i} data = {one} />)}
          </tbody>
        </Table>
      </Box>
      </App>
    );
  }
}

class MySelectedRow extends React.Component {
   render() {
      return (
         <TableRow>
            <td>{this.props.data.id}</td>
            <td>{this.props.data.name}</td>
            <td>{this.props.data.country}</td>
            <td>{this.props.data.continent}</td>
            <td>{this.props.data.type}</td>
         </TableRow>
      );
   }
}

// Add an table row for each entry
class MyTableRow extends React.Component {
   render() {
      return (
         <TableRow>
            <td><CheckBox/> {this.props.data.id}</td>
            <td>{this.props.data.name}</td>
            <td>{this.props.data.country}</td>
            <td>{this.props.data.continent}</td>
            <td>{this.props.data.type}</td>
         </TableRow>
      );
   }
}

// Search Function 
class MySearch extends React.Component {

    constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
		this.checkInput = this.checkInput.bind(this);
	}

    // if enter is pressed on the keyboard while typing:
    checkInput(event) {
		this.handleSubmit();
	}

    // when search is pressed or enter key is typed
    handleSubmit() {
		var query = this.refs.searchBox.value;
		console.log("[Main] search box get value : " + query);
    this.props.updateStateProp(query);
	}

    render() {
        return (
          <input onChange={this.checkInput} defaultValue="Search Airport"
            id="searchBox" ref="searchBox" 
            type="text"></input>
        );
    }
}

var element = document.getElementById('content');
ReactDOM.render(React.createElement(TripCo), element);