import React, {Component} from "react";
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
import './sensor.css'
import axios from "axios";
import 'sweetalert2/src/sweetalert2.scss';
import Swal from 'sweetalert2/dist/sweetalert2.js';
import Loader from 'react-loader-spinner';

export default class Sensors extends Component {
    _isMounted = false;
    constructor(props) {
        super(props);
        this.getAllSensorDetails = this.getAllSensorDetails.bind(this);
        this.activeSensor = this.activeSensor.bind(this);
        this.InactiveSensor = this.InactiveSensor.bind(this);
        this.sendStatus = this.sendStatus.bind(this);
        this.state = {
            sensorDetails: [],
            loaderStatus: true,
        }
    }

    componentDidMount() {
        this._isMounted = true;
        this.interval = setInterval(() => {
            this.getAllSensorDetails();
            this.sendStatus ();
        }, 10000);

    }

    componentWillUnmount() {
        this._isMounted = false;
    }


    activeSensor(sensor) {

        let sensorId = sensor.sensorId;
        const  updatedSensor ={
            sensorId :sensor.sensorId,
            floorNumber :sensor.floorNumber,
            roomNumber : sensor.roomNumber,
            smokeLevel :sensor.smokeLevel,
            co2Level : sensor.co2Level,
            status : 'Active'
        }
        this.state.sensorDetails.map(sen=>{
            if(sensor.sensorId===sen.sensorId){
                sen.status='Active';
            }
        })
        axios.post('http://localhost:8080/SensorController/updateSensor',updatedSensor).then(response => {
            Swal.fire(
                '',
                sensorId+' Is Activated .After Few Seconds you can notify it',
                'success'
            )
        }).catch(function (error) {
            console.log(error);
        })

    }

    sendStatus(){
        this.state.sensorDetails.map(sensor=>{
            axios.post('http://localhost:8080/SensorController/updateSensor',sensor).then(response => {
                // Swal.fire(
                //     '',
                //     sensorId+' Is Activated .After Few Seconds you can notify it',
                //     'success'
                // )
            }).catch(function (error) {
                console.log(error);
            })
        })
    }

    InactiveSensor(sensor) {
        let sensorId = sensor.sensorId;
        const  updatedSensor ={
            sensorId :sensor.sensorId,
            floorNumber :sensor.floorNumber,
            roomNumber : sensor.roomNumber,
            smokeLevel :parseInt("0"),
            co2Level : parseInt("0"),
            status : 'Inactive'
        }
        this.state.sensorDetails.map(sen=>{
            if(sensor.sensorId===sen.sensorId){
                sen.status='Inactive';
            }
        })
        axios.post('http://localhost:8080/SensorController/updateSensor',updatedSensor).then(response => {

            Swal.fire(
                '',
                sensorId+' Is Inactivated .After Few Seconds you can notify it',
                'success'
            )

        }).catch(function (error) {
            console.log(error);
        })

    }

    getAllSensorDetails() {
        axios.get('http://localhost:8080/SensorController/getAllSensorDetails').then(response => {
            if (this._isMounted) {
                this.setState({
                    sensorDetails: response.data,
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
                                        <MDBCardTitle className="h1-responsive pt-3 m-5 font-bold">Sensor Management
                                        </MDBCardTitle>

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
                                            <MDBCol size="4" className="MarginClass" key={sensor.sensorId}>
                                                <MDBCard style={{width: "22rem"}}>
                                                    <MDBCardBody>
                                                        <MDBCardTitle>{sensor.sensorId}</MDBCardTitle>
                                                        <MDBCardTitle>Floor Number :{sensor.floorNumber}
                                                        </MDBCardTitle>
                                                        <MDBCardTitle>Room Number :{sensor.roomNumber} </MDBCardTitle>
                                                        <MDBCardTitle>Co2 Level :<span
                                                            className="levelClass">{sensor.co2Level}</span>
                                                        </MDBCardTitle>
                                                        <MDBCardTitle>Smoke Level :<span
                                                            className="levelClass">{sensor.smokeLevel}</span>
                                                        </MDBCardTitle>
                                                        <MDBCardTitle>Status : <span
                                                            className="activeClass"> {sensor.status}</span>
                                                        </MDBCardTitle>
                                                        <MDBBtn onClick={() => this.activeSensor(sensor)}>Active
                                                            Status</MDBBtn>
                                                        <MDBBtn onClick={() => this.InactiveSensor(sensor)}>Inactive
                                                            Status</MDBBtn>

                                                    </MDBCardBody>
                                                </MDBCard>
                                            </MDBCol>
                                        )
                                    })

                                }


                            </MDBRow>

                        </MDBContainer>
                }
            </div>
        );
    }
}
