
async function getList(rcate){
	const result = await axios.get(`/reciperest/cate/${rcate}`)
	return result.data
}

async function getOne(rno){
	const response = await axios.get(`/reciperest/${rno}`)
	return response.data
}

async function addShop(shopObj){
	const response = await axios.post(`/shoprest/`, shopObj)
	return response.data
}

async function todayOrder(today){
	const response = await axios.get(`/shoprest/today/${today}`)
	return response.data
}

async function addShopState(shopStateObj){
	const response = await axios.post(`/shoprest/state/`, shopStateObj)
	return response.data
}

// 회원 정보 조회
async function getCustomer(cphone){
	const response = await axios.get(`/customerrest/phone/${cphone}`)
	return response.data
}

// 회원 포인트 적립
async function modifyCustomer(customerObj){
	const response = await axios.put(`/customerrest/${customerObj.cno}`, customerObj)
	return response.data
}

// 주문 내역에 회원 전화번호 업데이트
async function modifyShopState(shopStateObj){
	const response = await axios.put(`/shoprest/${shopStateObj.ssno}`, shopStateObj)
	return response.data
}

