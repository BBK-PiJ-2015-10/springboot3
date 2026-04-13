import React from "react";

class ListOfVideos extends React.Component {
    constructor(props) {
        super(props);
        this.state = {data: []}
    }

    async componentDidMount() {
        console.log("WOOF1")
        const response = await fetch("/api/videos")
        const json = await response.json()
        this.setState({data: json})
        console.log("WOOF2")
    }

    render() {
        return (
            <ul>
                {this.state.data.map(item =>
                    <li>
                        {item.name}
                    </li>
                )}
            </ul>
        )
    }
}

export default ListOfVideos;