/**
 * 
 */

const upload_el = document.querySelector("#uploadImg");
const upload_label = document.querySelector(".upload_el");

upload_el.addEventListener("change", function(e) {
	const prev_ul_el = document.querySelector(".prev_ul_el");
	prev_ul_el.innerHTML = "";

	if (this.files.length === 1) {
		prev_ul_el.style.justifyContent = "center";
	} else prev_ul_el.style.justifyContent = "start";

	if (this.files.length > 0) {
		upload_label.innerHTML = `<i class="fa-solid fa-circle-check"></i> done`;
	} else {
		upload_label.innerHTML = `<i class="fa-regular fa-image"></i> choose photo`;
	}

	for (let i = 0; i < this.files.length; i++) {
		let fileReader = new FileReader();
		fileReader.readAsDataURL(this.files[i]);
		fileReader.addEventListener("load", () => {
			let li_str = `
            <li>
              <img src="${fileReader.result}">
              <div>${this.files[i].name}</div>
            </li>
            `;

			prev_ul_el.insertAdjacentHTML("beforeend", li_str);
		});
	}
});
