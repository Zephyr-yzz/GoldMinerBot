# Gold Miner Tycoon Bot

Gold Miner Tycoon is a fun and addictive clicker game hosted on Telegram. In this game, players can dig for gold, upgrade their tools, expand their mines, and become the ultimate mining tycoon—all through simple clicks!

## Features

- **Click to Mine**: Tap to dig for gold and accumulate wealth.
- **Upgrade Tools**: Use your gold to upgrade your mining tools and increase your earnings.
- **Hire Miners**: Automate the mining process by hiring miners to work for you.
- **Expand Your Empire**: Unlock new mines and increase your production.

## Installation

### Prerequisites

- Python 3.7 or higher
- Telegram Bot API token (Create a bot using [BotFather](https://core.telegram.org/bots#botfather) on Telegram)

### Setup

1. **Clone the repository**:

    ```bash
    git clone https://github.com/yourusername/gold-miner-tycoon-bot.git
    cd gold-miner-tycoon-bot
    ```

2. **Install dependencies**:

    ```bash
    pip install -r requirements.txt
    ```

3. **Set up your environment**:

    Create a `.env` file in the root directory and add your Telegram Bot API token:

    ```plaintext
    TOKEN=your_telegram_bot_token
    ```

4. **Run the bot**:

    ```bash
    python bot.py
    ```

## Deployment

### Deploying on Railway

1. **Create a new project on Railway**.
2. **Connect your GitHub repository** or upload your code directly.
3. **Set environment variables** in Railway's dashboard:
    - `TOKEN=your_telegram_bot_token`
4. **Deploy the bot**: Railway will automatically build and deploy your project.

### Alternative Hosting Platforms

You can also deploy this bot on other platforms like Vercel, Render, or AWS Lambda. Make sure to follow the respective platform's deployment guidelines.

## Usage

1. **Start the Bot**: Search for `@GoldMinerTycoonBot` on Telegram and start a chat.
2. **Begin Mining**: Use the `/mine` command to start digging for gold.
3. **Upgrade**: Use `/upgrades` to view and purchase upgrades.
4. **Expand**: Unlock new mining areas to increase your gold production.

## Contributing

Contributions are welcome! Feel free to submit a pull request or open an issue to discuss improvements or report bugs.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.



---

Enjoy playing Gold Miner Tycoon and becoming the richest miner in the land!

