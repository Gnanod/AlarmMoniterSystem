import React, {Component} from "react";
import {Bar, Line, Pie} from 'react-chartjs-2';


export default class SensorChart extends Component {


    render() {
        const {chartData} = this.props;
        console.log(chartData);
        return (
            <div className="chart">
                <Bar
                    data={
                        chartData
                    }
                    options={{
                        title: {
                            display: true,
                            text: 'Active Sensor Details',
                            fontSize: 15
                        },
                        legend: {
                            display: true,
                            position: 'right'
                        },

                    }}

                    width={100}
                    height={50}
                />
            </div>
        );
    }
}
