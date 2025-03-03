import React from "react";
import { Link } from "react-router-dom";

const Footer = () => {
  return (
    <footer className="footer bg-dark text-white text-center py-3">
      <div className="container">
        <p className="mb-1">
          &copy; {new Date().getFullYear()} Bus Ticket Booking | API Integrated
        </p>
        
        {/* Quick Links */}
        <div className="footer-links d-flex justify-content-center gap-3">
          <Link to="/contact" className="text-white text-decoration-none">Contact Us</Link>
          <Link to="/privacy" className="text-white text-decoration-none">Privacy Policy</Link>
          <Link to="/faq" className="text-white text-decoration-none">FAQs</Link>
        </div>

        {/* Social Media Icons */}
        <div className="mt-2">
          <a href="https://facebook.com" target="_blank" rel="noopener noreferrer" className="text-white mx-2">
            <i className="fab fa-facebook"></i>
          </a>
          <a href="https://twitter.com" target="_blank" rel="noopener noreferrer" className="text-white mx-2">
            <i className="fab fa-twitter"></i>
          </a>
          <a href="https://instagram.com" target="_blank" rel="noopener noreferrer" className="text-white mx-2">
            <i className="fab fa-instagram"></i>
          </a>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
