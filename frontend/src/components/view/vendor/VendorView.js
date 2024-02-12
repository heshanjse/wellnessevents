import React, { useState, useEffect } from "react";
import { request, setAuthHeader } from "../../util/AxiosHelper";
import { Modal, Button, Table, Form } from "react-bootstrap";
import "./VendorView.css";
import NoDataFound from "../../util/NoDataFound";

const VendorView = ({ accessToken, userRole, userCompany }) => {
    const [data, setData] = useState([]);
    const [selectedEvent, setSelectedEvent] = useState(null);
    const [showModal, setShowModal] = useState(false);
    const [action, setAction] = useState("");
    const [selectedDate, setSelectedDate] = useState("");
    const [rejectionReason, setRejectionReason] = useState("");
    const [confirmVisible, setConfirmVisible] = useState(false);

    useEffect(() => {
        const fetchVendorEvents = async () => {
            try {
                const authorization = {
                    Authorization: `Bearer ${accessToken}`,
                }
                const response = await request('get', `/api/v1/vendor/event/${userRole}/${userCompany}`, null, authorization);
                (response) ? setData(response.data) : setData(null);
            } catch (error) {
                console.error("Error fetching Vendor events:", error);
                if (error.response && error.response.status === 401) {
                    setAuthHeader(null);
                    // Handle unauthorized access later
                }
            }
        };

        fetchVendorEvents();
    }, [accessToken]);

    const handleRowClick = (eventid) => {
        const selectedEvent = data.find((event) => event.eventId === eventid);
        if (selectedEvent) {
            setSelectedEvent(selectedEvent);
            selectedEvent?.status === 'Pending' ? setShowModal(true) : setShowModal(false);
        }
    };

    const handleClose = () => {
        setAction("");
        setShowModal(false);
        setConfirmVisible(false);
    };

    const handleApprove = () => {
        setAction("approve");
        setConfirmVisible(true);
    };

    const handleReject = () => {
        setAction("reject");
        setConfirmVisible(true);
    };

    const handleDateChange = (e) => {
        setSelectedDate(e.target.value);
    };

    const handleRejectionReasonChange = (e) => {
        setRejectionReason(e.target.value);
    };

    const handleConfirm = async () => {
        try {
            const authorization = {
                Authorization: `Bearer ${accessToken}`,
            };
            const response = await request(
                'post',
                '/api/v1/vendor/event',
                {
                    eventId: selectedEvent?.eventId,
                    status: action === 'approve' ? 'Approved' : 'Rejected',
                    confirmedDate: action === 'approve' ? selectedDate : selectedEvent?.confirmedDate,
                    remarks: action === 'reject' ? rejectionReason : selectedEvent?.rejectionReason,
                }, authorization);
            const updatedEvent = response.data;
            const updatedData = data.map((event) =>
                event.eventId === updatedEvent.eventId ? updatedEvent : event
            );

            setData(updatedData);
            setShowModal(false);
        } catch (error) {
            console.error("Error updating event:", error);
            // Handle error (e.g., show an error message)
        }
    };

    const sortedData = data.sort((a, b) => {
        const statusOrder = { Pending: 1, Approved: 2, Rejected: 3 };
        return statusOrder[a.status] - statusOrder[b.status];
    });

    const getStatusColor = (status) => {
        switch (status) {
            case "Pending":
                return "#FFA500";
            case "Approved":
                return "#00FF00";
            case "Rejected":
                return "#DC3545";
            default:
                return "#000000";
        }
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
                        </tr>
                    </thead>
                    <tbody>
                        {sortedData.map((event) => (
                            <tr
                                key={event.eventId}
                                onClick={() => handleRowClick(event.eventId)}
                                style={{
                                    cursor: "pointer",
                                    backgroundColor: getStatusColor(event.status),
                                    color: "#fff",
                                }}
                            >
                                <td>{event.eventName}</td>
                                <td>{event.vendorName}</td>
                                <td>
                                    {Array.isArray(event.confirmedDate)
                                        ? event.confirmedDate.join(", ")
                                        : event.confirmedDate}
                                </td>
                                <td>{event.status}</td>
                                <td>{event.createdDate}</td>
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
                {console.log("hdhdh")}
                {console.log(selectedEvent?.confirmedDate)}
                {action === "" && (
                    <Modal.Footer>
                        <Button variant="success" onClick={handleApprove}>
                            Approve
                        </Button>
                        <Button variant="danger" onClick={handleReject}>
                            Reject
                        </Button>
                        {confirmVisible && (
                            <Button variant="primary" onClick={handleConfirm}>
                                Confirm
                            </Button>
                        )}
                    </Modal.Footer>
                )}

                {action === "approve" && (
                    <Modal.Footer className="vendor-view-footer-approve">
                        <Form.Group
                            controlId="dateSelect"
                            className="vendor-view-select-date"
                        >
                            <Form.Label>Select Date:</Form.Label>
                            <Form.Control
                                as="select"
                                value={selectedDate}
                                onChange={handleDateChange}
                            >
                                {selectedEvent?.confirmedDate &&
                                    selectedEvent.confirmedDate.split(",").map((date) => (
                                        <option key={date} value={date}>
                                            {date}
                                        </option>
                                    ))}
                            </Form.Control>
                        </Form.Group>
                        {confirmVisible && (
                            <Button variant="primary" onClick={handleConfirm}>
                                Confirm
                            </Button>
                        )}
                    </Modal.Footer>
                )}

                {action === "reject" && (
                    <Modal.Footer className="vendor-view-footer-reject">
                        <Form.Group controlId="rejectionReason">
                            <Form.Label>Rejection Reason:</Form.Label>
                            <Form.Control
                                as="textarea"
                                rows={3}
                                value={rejectionReason}
                                onChange={handleRejectionReasonChange}
                            />
                        </Form.Group>
                        {confirmVisible && (
                            <Button variant="primary" onClick={handleConfirm}>
                                Confirm
                            </Button>
                        )}
                    </Modal.Footer>
                )}
            </Modal>
        </div>
    );
};

export default VendorView;