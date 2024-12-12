/**
 * 
 */
const citySelect = document.querySelector("#city");
const areaSelect = document.querySelector("#area");
const roadSelect = document.querySelector("#road");
let addressData = [];
let cityArray = [];
let areaArray = [];
document.addEventListener("DOMContentLoaded", fetchData);

async function fetchData() {
	const res = await fetch("../data/addrData.json");
	const data = await res.json();
	addressData = data;

	addressData.forEach((city) => {
		const option = document.createElement("option");
		option.value = city.CityName;
		option.textContent = city.CityName;
		citySelect.appendChild(option);
	});

	citySelect.selectedIndex = 1;
	loadArea();
}

function loadArea() {
	if (citySelect.selectedIndex == 0) {
		areaSelect.innerHTML = "<option value=''>請選擇</option>";
		roadSelect.innerHTML = "<option value=''>請選擇</option>";
		return;
	}

	areaSelect.innerHTML = "";
	cityArray = addressData.find(
		(city) => city.CityName === citySelect.value
	);

	cityArray.AreaList.forEach((area) => {
		const option = document.createElement("option");
		option.value = area.AreaName;
		option.textContent = area.AreaName;
		areaSelect.appendChild(option);
	});

	loadRoad();
}

function loadRoad() {
	roadSelect.innerHTML = "";

	const selectAreaArr = cityArray.AreaList.find(
		(area) => area.AreaName === areaSelect.value
	);

	selectAreaArr.RoadList.forEach((road) => {
		const option = document.createElement("option");
		option.value = road.RoadName;
		option.textContent = road.RoadName;
		roadSelect.appendChild(option);
	});
}
