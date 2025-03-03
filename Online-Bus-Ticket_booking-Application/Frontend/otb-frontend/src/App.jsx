import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import SeatSelection from './pages/SeatSelection';
import Payment from './pages/Payment';
import Confirmation from './pages/Confirmation';
import PassengerDetails from './pages/PassengerDetails';
import Profile from './pages/Profile';
import Navbar from './components/Navbar';
import Footer from './components/Footer';
import ReviewBooking from './pages/ReviewBooking';
import NotFound from './pages/NotFound';
import { AuthProvider } from './context/AuthContext';
import { AnimatePresence } from 'framer-motion';

function App() {
  return (
    <AuthProvider>
      <Router>
        <Navbar /> {/* Persistent Navbar */}
        <AnimatePresence mode="wait">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/booking/:busId" element={<SeatSelection />} />
            <Route path="/review-booking" element={<ReviewBooking />} />
            <Route path="/payment" element={<Payment />} />
            <Route path="/confirmation/:bookingId" element={<Confirmation />} />
            <Route path="/passenger-details" element={<PassengerDetails />} />
            <Route path="/profile" element={<Profile />} />
            <Route path="*" element={<NotFound />} /> {/* Handles 404 pages */}
          </Routes>
        </AnimatePresence>
        <Footer /> {/* Persistent Footer */}
      </Router>
    </AuthProvider>
  );
}

export default App;
