const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const phone = new Schema({
    manufacturer: { type: String },
    name: { type: String },
    price: { type: String },
    image: { type: String },
    category: { type: String },
    display: { type: String },
    system: { type: String },
    camera: { type: String },
    cpu: { type: String },
    disk1: { type: String },
    disk2: { type: String },
    sim: { type: String },
    pin: { type: String },
});

module.exports = mongoose.model('phone', phone);