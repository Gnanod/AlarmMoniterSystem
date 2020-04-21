import React, {Component} from "react";
import axios from "axios";
import {
    MDBBtn,
    MDBCard,
    MDBCardBody,
    MDBCardText,
    MDBCardTitle,
    MDBCol,
    MDBContainer,
    MDBJumbotron,
    MDBRow
} from "mdbreact";
import SensorChart from "./sensorchart";
import Loader from 'react-loader-spinner';
import './sensor.css'
import * as pattern from "patternomaly";

export default class SensorBody extends Component {
    _isMounted = false;

    constructor(props) {
        super(props);

        // this.getSensorDetails = this.getSensorDetails.bind(this);
        this.diplayDetails = this.diplayDetails.bind(this);

        this.state = {
            sensorDetails: [],
            loaderStatus: true,
            chartData: [],
            Data: {}
        }
    }

    componentDidMount() {
        this._isMounted = true;
        this.interval = setInterval(() => {
            this.diplayDetails();
        }, 40000);
    }

    componentWillUnmount() {
        this._isMounted = false;
    }


    diplayDetails() {
        axios.get('http://localhost:8080/SensorController/getActiveSensorDetails').then(response => {

            if (this._isMounted) {

                const sensorResponse = response.data;
                const newSensors = [];
                let smokeColor ;
                let co2Color ;

                for (let sensor in sensorResponse) {
                    const newValueData = [
                        0,
                        sensorResponse[sensor].co2Level,
                        sensorResponse[sensor].smokeLevel
                    ]

                    if(sensorResponse[sensor].co2Level >=5){
                        smokeColor = 'rgba(255,0,0,1.0)';
                    }else{
                        smokeColor = 'rgba(75,192,192,1.0)';
                    }

                    if(sensorResponse[sensor].smokeLevel >=5){
                        co2Color = 'rgba(255,0,0,1.0)';
                    }else{
                        co2Color = 'rgba(75,192,192,1.0)';
                    }
                    const newData = {
                        labels: ['', 'CO2', 'Smoke'],
                        datasets: [{
                            label: 'Level',
                            data: newValueData,
                            backgroundColor:[
                                '',
                                smokeColor,
                                co2Color
                            ]
                        }

                        ],

                    }
                    newSensors.push({
                        sensorId: sensorResponse[sensor].sensorId,
                        floorNumber: sensorResponse[sensor].floorNumber,
                        roomNumber: sensorResponse[sensor].roomNumber,
                        data: newData
                    });

                } //end for
                this.setState({
                    sensorDetails: newSensors,
                    loaderStatus: false
                });
            }


        }).catch(function (error) {
            console.log(error);
        })
    }

    render() {
        return (

            <div>
                <MDBContainer>
                    <MDBRow>
                        <MDBCol>
                            <MDBJumbotron style={{padding: 0}}>
                                <MDBCol className="text-white text-center  px-2 my-2"
                                        style={{backgroundImage: `url(https://mdbootstrap.com/img/Photos/Others/gradient1.jpg)`}}>
                                    <MDBCol className="py-5">
                                        <MDBCardTitle className="h1-responsive pt-3 m-5 font-bold">Seonsor
                                            Details</MDBCardTitle>

                                    </MDBCol>
                                </MDBCol>
                            </MDBJumbotron>
                        </MDBCol>
                    </MDBRow>
                </MDBContainer>

                {
                    this.state.loaderStatus ?
                        <Loader className="loaderClass"

                                type="Audio"
                                color="#00BFFF"
                                height={400}
                                width={250}
                                timeout={30000} //3 secs

                        /> :

                        <MDBContainer>
                            <MDBRow>
                                {
                                    this.state.sensorDetails.map(sensor => {
                                        return (
                                            <MDBCol size="6" className="marginCss" key={sensor.sensorId}>
                                                <MDBCard style={{width: "33rem"}}>
                                                    <MDBCardBody>
                                                        <MDBCardTitle>{sensor.sensorId}</MDBCardTitle>
                                                        <MDBCardTitle>Floor Number
                                                            : {sensor.floorNumber} </MDBCardTitle>
                                                        <MDBCardTitle>Room Number : {sensor.roomNumber}</MDBCardTitle>

                                                        <SensorChart
                                                            chartData={sensor.data}
                                                        />

                                                    </MDBCardBody>
                                                </MDBCard>
                                            </MDBCol>
                                        )
                                    })
                                }


                            </MDBRow>
                            <br/>


                        </MDBContainer>
                }

            </div>
        );
    }
}
