import React, { useState, useEffect } from "react";
import { request, setAuthHeader } from "../util/AxiosHelper";
import "bootstrap/dist/css/bootstrap.min.css";
import { Modal, Button, Table } from "react-bootstrap";
import NoDataFound from "../util/NoDataFound";


const HrView = ({ accessToken, userRole, userCompany }) => {
    const [data, setData] = useState([]);
    const [selectedEvent, setSelectedEvent] = useState(null);
    const [showModal, setShowModal] = useState(false);

    useEffect(() => {
        const fetchHrEvents = async () => {
            const headers = {
                Authorization: `Bearer ${accessToken}`,
            };

            try {
                const authorization = {
                    Authorization: `Bearer ${accessToken}`,
                }
                const response = await request('get', `/api/v1/human-resource/event/${userRole}/${userCompany}`, null, authorization);
                (response) ? setData(response.data) : setData(null);

            } catch (error) {
                console.error("Error fetching HR events:", error);
                if (error.response && error.response.status === 401) {
                    setAuthHeader(null);
                    // Handle unauthorized access later
                }
            }
        };

        fetchHrEvents();
    }, [accessToken]);

    const handleViewClick = (eventid) => {
        const selectedEvent = data.find((event) => event.eventId === eventid);
        if (selectedEvent) {
            setSelectedEvent(selectedEvent);
            setShowModal(true);
        }
    };

    const handleClose = () => {
        setShowModal(false);
    };

    return (
        <div className="container mt-4">
            {data ? (
                <Table striped bordered hover>
                    <thead>
                        <tr>
                            <th>Event Name</th>
                            <th>Vendor Name</th>
                            <th>Confirmed Dates</th>
                            <th>Status</th>
                            <th>Date Created</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {data.map((event) => (
                            <tr key={event.eventId}>
                                <td>{event.eventName}</td>
                                <td>{event.vendorName}</td>
                                <td>
                                    {Array.isArray(event.confirmedDate)
                                        ? event.confirmedDate.join(", ")
                                        : event.confirmedDate}
                                </td>
                                <td>{event.status}</td>
                                <td>{event.createdDate}</td>
                                <td>
                                    <Button
                                        variant="primary"
                                        onClick={() => handleViewClick(event.eventId)}
                                    >
                                        View
                                    </Button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
            ) : (
                <NoDataFound />
            )}

            <Modal show={showModal} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Event Details</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {console.log("selected event 1:")}
                    {console.log(selectedEvent)}
                    <h5>{selectedEvent?.eventName}</h5>
                    <p>Vendor Name: {selectedEvent?.vendorName}</p>
                    <p>
                        Confirmed Dates:{" "}
                        {Array.isArray(selectedEvent?.confirmedDate)
                            ? selectedEvent?.confirmedDate.join(", ")
                            : selectedEvent?.confirmedDate}
                    </p>
                    <p>Status: {selectedEvent?.status}</p>
                    <p>Date Created: {selectedEvent?.createdDate}</p>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
};

export default HrView;


