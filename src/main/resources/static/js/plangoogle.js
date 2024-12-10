const CLIENT_ID = '402761261358-8j4oatc61kdgn23bbuk0mqk8s7hgoco7.apps.googleusercontent.com';
const API_KEY = 'AIzaSyDTFVY_ElsUuIPbk8LH79BdNvp4XFdQB2g';

let tokenClient;
let gapiInitialized = false;
let gisInitialized = false;

async function initializeGoogleApi() {
    try {
        await new Promise((resolve) => gapi.load('client', resolve));
        await gapi.client.init({
            apiKey: API_KEY,
            discoveryDocs: ['https://www.googleapis.com/discovery/v1/apis/calendar/v3/rest']
        });

        tokenClient = google.accounts.oauth2.initTokenClient({
            client_id: CLIENT_ID,
            scope: 'https://www.googleapis.com/auth/calendar.events',
            callback: ''  // 將在需要時動態設置
        });

        gapiInitialized = true;
        gisInitialized = true;
        console.log('Google APIs initialized successfully');
    } catch (error) {
        console.error('Error initializing Google APIs:', error);
    }
}

window.addEventListener('load', initializeGoogleApi);

async function addToGoogleCalendar() {
    const btn = document.getElementById('calendarButton');
    btn.disabled = true;
    const originalContent = btn.innerHTML;
    btn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> 處理中...';

    try {
        if (!gapiInitialized || !gisInitialized) {
            throw new Error('API 尚未初始化完成');
        }

        const eventName = document.getElementById('eventName').value;
        const eventDate = document.getElementById('eventDate').value;
        const orderId = document.getElementById('orderId').value;

        const startTime = new Date(eventDate);
        const endTime = new Date(startTime);
        endTime.setDate(endTime.getDate() + 1); // 假設行程為一天

        const event = {
            'summary': `${eventName}`,
            'description': `訂單編號: ${orderId}`,
            'start': {
                'date': eventDate,
                'timeZone': 'Asia/Taipei'
            },
            'end': {
                'date': endTime.toISOString().split('T')[0],
                'timeZone': 'Asia/Taipei'
            },
            'reminders': {
                'useDefault': false,
                'overrides': [
                    {'method': 'popup', 'minutes': 24 * 60}  // 提前一天提醒
                ]
            }
        };

        return new Promise((resolve, reject) => {
            tokenClient.callback = async (resp) => {
                if (resp.error !== undefined) {
                    reject(resp);
                    return;
                }
                try {
                    const result = await gapi.client.calendar.events.insert({
                        'calendarId': 'primary',
                        'resource': event
                    });
                    resolve(result);
                } catch (err) {
                    reject(err);
                }
            };
            tokenClient.requestAccessToken();
        }).then(response => {
            if (response && response.result && response.result.htmlLink) {
                alert('行程已成功加入 Google 日曆！');
                window.open(response.result.htmlLink);
            }
        });

    } catch (error) {
        console.error('Error:', error);
        alert('新增失敗：' + (error.message || '請稍後再試'));
    } finally {
        btn.disabled = false;
        btn.innerHTML = originalContent;
    }
}